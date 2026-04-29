# CIVA Reduction

A WebXR-capable scientific volume viewer for CT and scalar-field datasets, with **topology-aligned reduction** powered by the [Topology ToolKit (TTK)](https://topology-tool-kit.github.io/), spatial Level-of-Detail (LOD) decimation, and an interactive Region-of-Interest (ROI) refinement workflow.

The frontend is a Vite + React + TypeScript single-page app rendered through [vtk.js](https://kitware.github.io/vtk-js/). The backend is a FastAPI service that runs TTK + VTK pipelines on demand.

---

## Table of Contents

1. [Highlights](#highlights)
2. [Screenshots and feature tour](#screenshots-and-feature-tour)
3. [Architecture](#architecture)
4. [Tech stack](#tech-stack)
5. [Datasets bundled with the project](#datasets-bundled-with-the-project)
6. [Quick start](#quick-start)
7. [Backend setup (TTK)](#backend-setup-ttk)
8. [Configuration reference](#configuration-reference)
9. [Project layout](#project-layout)
10. [Module-by-module reference](#module-by-module-reference)
11. [Topology reduction pipeline](#topology-reduction-pipeline)
12. [Adding and switching datasets](#adding-and-switching-datasets)
13. [API reference](#api-reference)
14. [Engineering standards](#engineering-standards)
15. [Troubleshooting](#troubleshooting)
16. [Changelog of recent improvements](#changelog-of-recent-improvements)
17. [License](#license)

---

## Highlights

- **Real topology reduction** via TTK (`ttkPersistenceDiagram` → persistence threshold → `ttkTopologicalSimplification`), exposed through a single `POST /api/reduce` endpoint.
- **Spatial LOD** with four levels (`full`, `high`, `medium`, `low`) computed by `vtkImageShrink3D` (1×, 1×, 2×, 4×) on top of the topology step.
- **Drag-and-drop dataset switching**: any `.vti` placed in `data/datasets/` is auto-discovered and selectable from a dropdown. Files outside the data folder can be loaded one-off via a browser file picker.
- **Robust scalar handling**: VTI files with no active scalar, multi-component arrays, or only cell-data scalars are handled automatically — the loader synthesizes a renderable 1-component scalar field if needed.
- **Interactive ROI** with X / Y / Z position sliders and radius. The wireframe sphere can be moved anywhere in the volume; ROI refinement overlays a higher-detail volume crop in that region.
- **Feature operators**: slice plane, isosurface (Marching Cubes), threshold region, contextual dim.
- **Auto LOD by camera distance**: optional automatic LOD switching based on `LOD_*` thresholds.
- **WebXR (immersive-VR)** when the browser supports it; graceful fallback otherwise.
- **Reduction-state FSM** (`idle → base_volume → lod_switched → roi_refined → feature_focus`) so the UI never drifts from data state.
- **Metrics dashboard**: FPS μ/min/max, frame ms p95, action latencies, volume load time, voxel count.
- **Session export**: dump session events + metrics as JSON for offline analysis.

---

## Screenshots and feature tour

| Feature | What it does |
| --- | --- |
| LOD buttons (Full / High / Medium / Low) | Re-fetches the volume through the backend at the chosen spatial reduction level |
| DATASET dropdown | Auto-populated from `/data/datasets/index.json`; selecting a value triggers a TTK-reduced reload |
| Load .vti file | Opens the OS file picker and loads any local VTI as a Blob URL (static, no reduction) |
| Display intensity slider | Opacity floor — `0` shows everything, higher values hide soft tissue / noise. No reload |
| Topology persistence (driven by the LOD reload) | Normalized `[0,1]` value sent to TTK as `persistenceThreshold` |
| Slice plane | Single clipping plane through the volume center |
| Isosurface | Marching Cubes contour at a chosen scalar value |
| Threshold region | Visualizes only voxels whose scalar lies in `[min, max]` |
| Dim volume | Lowers the contextual volume opacity for "feature focus" |
| ROI wireframe | Cyan sphere actor at any (X, Y, Z) position with a configurable radius |
| ROI local refinement | Crops the current volume to the ROI bounds and renders a second, higher-detail volume on top |
| Auto LOD by distance | Suggests an LOD level based on camera distance to volume center |
| Reset exploration | Restores all sliders, toggles, ROI, and LOD to defaults |
| Export session JSON | Downloads metrics + event log as a single JSON file |

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                              Browser                                    │
│                                                                         │
│   ┌──────────────┐  ┌────────────────┐  ┌────────────────────────────┐  │
│   │   React UI   │→ │  central store │→ │  vtk.js scene & renderer   │  │
│   │  (App.tsx)   │  │   (state/*)    │  │      (core/renderer/*)     │  │
│   └──────────────┘  └────────┬───────┘  └────────────────────────────┘  │
│         ↑                    │                                          │
│         │                    ▼                                          │
│   ┌──────────────────────────────────────┐                              │
│   │          data loader (data/*)        │                              │
│   │  loadVolumeWithProgress, loadVti,    │                              │
│   │  loadVtiFromFile, loadDescriptor     │                              │
│   └──────────────┬───────────────────────┘                              │
│                  │                                                      │
│                  │ static (vite middleware)        backend (proxied)    │
│                  ▼                                  ▼                   │
│        ┌──────────────────────┐        ┌─────────────────────────────┐  │
│        │  /data/datasets/*    │        │  /api/reduce  /api/health   │  │
│        │  (vite plugin reads  │        │  (Vite proxies to FastAPI)  │  │
│        │   from disk)         │        │                             │  │
│        └──────────────────────┘        └──────────────┬──────────────┘  │
└────────────────────────────────────────────────────────│─────────────────┘
                                                        ▼
                                       ┌───────────────────────────────┐
                                       │     FastAPI backend           │
                                       │     backend/main.py           │
                                       │     backend/reduce.py         │
                                       │                               │
                                       │  TTK persistence diagram →    │
                                       │  topological simplification → │
                                       │  vtkImageShrink3D LOD →       │
                                       │  return VTI bytes             │
                                       └───────────────────────────────┘
```

### Request flow on dataset load

1. App boot: `main.tsx` validates `appConfig`, mounts `<App />`.
2. `App.tsx` probes `GET /api/health` to know if TTK is reachable.
3. Dropdown is populated from `GET /data/datasets/index.json` (Vite middleware lists `data/datasets/*.vti`).
4. User changes LOD or selects a dataset → `loadVolumeWithProgress(id, level, ...)`:
   - If the backend is enabled and reachable: `POST /api/reduce` with `{ datasetId, level, persistenceThreshold }`.
   - Else: fetch `/data/datasets/<id>.vti` directly (static fallback).
5. The returned VTI bytes are parsed by `vtkXMLImageDataReader`, the active scalar is auto-resolved (`ensureRenderableScalars`), and the resulting `vtkImageData` is handed to `SceneManager.setVolumeData`.
6. The reduction-phase FSM updates derived state (`base_volume / lod_switched / roi_refined / feature_focus`), and the dashboard reflects it.

---

## Tech stack

**Frontend**
- Vite 5 (dev server with custom middleware for `data/datasets/`)
- React 18 + TypeScript (strict mode)
- vtk.js 30.7 (VolumeMapper, MarchingCubes, ImageCropFilter, SphereSource, …)
- WebXR Device API (immersive-vr)

**Backend**
- Python 3.11
- FastAPI + Uvicorn
- VTK (XML I/O, ImageShrink3D, Threshold, ProbeFilter)
- TTK (`topologytoolkit` from conda-forge) — `ttkPersistenceDiagram`, `ttkTopologicalSimplification`

**DevOps**
- Docker / Docker Compose for cross-platform TTK (the conda-forge `topologytoolkit` package has no `osx-arm64` build, so Apple Silicon users run the backend in a Linux container)

---

## Datasets bundled with the project

`data/datasets/` is the canonical place for source `.vti` files. Two are committed:

| File | Type | Dimensions | Spacing | Scalar layout | Notes |
| --- | --- | --- | --- | --- | --- |
| `ctBones.vti` | CT bone scan | 256 × 256 × 256 | 1.0 × 1.0 × 1.0 | `Float64`, single 1-component array `ImageScalars`, range 0–255 | Primary demo dataset; large (~11 MB compressed). Renders bones for opacity-floor tuning, ROI, isosurface |
| `waveletElevation.vti` | Synthetic wavelet decomposition | 21 × 21 × 21 | 1.0 × 1.0 × 1.0 | `Float32`, single 2-component array `RDataWithElevation`, range ≈ 37 – 277 | Tiny (~74 KB); demonstrates the multi-component auto-extraction path. Component 0 (elevation) is rendered |

A descriptor file (`ctBones.descriptor.json`) ships alongside the bones dataset:

```json
{
  "id": "ctBones",
  "gridDimensions": [256, 256, 256],
  "resolutionLevel": "fine",
  "lodLevel": "high",
  "scalarFields": [
    { "name": "ImageScalars", "numberOfComponents": 1, "dataType": "Float64", "range": [0, 255] }
  ],
  "voxelCount": 16777216,
  "spacing": [1, 1, 1],
  "origin": [0, 0, 0],
  "path": "ctBones.vti"
}
```

Descriptor files are **optional**. When missing, the dashboard auto-derives the same metadata from the loaded `vtkImageData` via `descriptorFromVtkImageData()`.

---

## Quick start

### Prerequisites

- Node.js 18.20.7 (pinned in `.nvmrc` and `package.json` engines)
- npm 9+
- Docker Desktop (recommended for backend on Apple Silicon)

### Frontend

```bash
npm install
npm run dev
```

Open http://localhost:5173. With no backend running you will see "Reduction: Backend unreachable" and the app falls back to **static** loading (no real reduction, just file fetches).

### Full stack (frontend + TTK backend)

```bash
# Terminal A — backend in Docker (Linux container, TTK pre-installed)
docker compose up --build

# Terminal B — frontend
npm run dev
```

The dashboard should now show **"Reduction: Topology ToolKit (TTK)"** in indigo. The persistence slider and LOD buttons trigger real `POST /api/reduce` calls.

### npm scripts

| Script | What it does |
| --- | --- |
| `npm run dev` | Vite dev server with HMR (port 5173) |
| `npm run build` | Type-check (`tsc -b`) + production bundle |
| `npm run preview` | Serve the production build |
| `npm run type-check` | TypeScript only, no emit |
| `npm run lint` | ESLint, zero-warning enforced |
| `npm run format` | Prettier write |
| `npm run format:check` | Prettier check |

---

## Backend setup (TTK)

### Option A — Docker (recommended for Apple Silicon)

`docker-compose.yml` builds an `amd64` Linux image with TTK from conda-forge and mounts `data/datasets/` read-only into the container.

```bash
docker compose up --build
# probe:
curl http://localhost:8000/api/health
# → {"ok":true,"vtk":true,"ttk":true,"dataDir":"/data/datasets"}
```

### Option B — local Python venv (no TTK, VTK fallback only)

```bash
cd backend
python -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
DATA_DIR=../data/datasets uvicorn main:app --reload --port 8000
```

Without TTK, `POST /api/reduce` returns HTTP 503. The app detects this and falls back to static loading.

### Option C — conda env with TTK (Linux/Intel macOS)

```bash
chmod +x backend/setup_ttk.sh && ./backend/setup_ttk.sh
conda activate civa-backend
cd backend && uvicorn main:app --reload --port 8000
```

---

## Configuration reference

All knobs live in `.env` (consumed by `src/config/appConfig.ts`).

### Dataset

| Variable | Default | Purpose |
| --- | --- | --- |
| `VITE_DATA_PATH` | `/data/datasets` | Base URL the frontend uses to fetch static VTI |
| `VITE_DEFAULT_DATASET` | `ctBones` | Dataset id loaded on page open |
| `VITE_DATA_MAX_SIZE_MB` | `1000` | Soft size limit for sanity checks |
| `VITE_VOLUME_DATA_FORMAT` | `vti` | `vti` or `vtkjs` (HttpDataSetReader index.json) |
| `VITE_REDUCTION_API_URL` | empty in dev / `http://localhost:8000` in prod | Direct backend URL; empty → use Vite proxy |
| `VITE_REDUCTION_PROXY_TARGET` | `http://localhost:8000` | Where Vite proxies `/api/*` |
| `VITE_REDUCTION_FETCH_TIMEOUT_MS` | `15000` | Reduce-call timeout |

### LOD

| Variable | Default |
| --- | --- |
| `VITE_LOD_MEDIUM_THRESHOLD_M` | 50 |
| `VITE_LOD_LOW_THRESHOLD_M` | 150 |
| `VITE_LOD_MAX_DISTANCE_M` | 500 |
| `VITE_LOD_MIN_POINT_SIZE_PX` | 1 |
| `VITE_LOD_MAX_POINT_SIZE_PX` | 10 |

### ROI

| Variable | Default |
| --- | --- |
| `VITE_ROI_MIN_SIZE_M` | 1 |
| `VITE_ROI_MAX_SIZE_M` | 10000 |
| `VITE_ROI_DEFAULT_SHAPE` | `box` |
| `VITE_ROI_DEFAULT_SIZE_M` | 100 |
| `VITE_ROI_BOOST_LOD_WHEN_ACTIVE` | `true` |

### Rendering

| Variable | Default |
| --- | --- |
| `VITE_RENDER_DEFAULT_OPACITY` | 0.8 |
| `VITE_RENDER_SAMPLE_DISTANCE_M` | 0 |
| `VITE_RENDER_POINT_SIZE_PX` | 2 |
| `VITE_RENDER_BG_COLOR` | `#000000` |
| `VITE_RENDER_GRID_ENABLED` | `true` |
| `VITE_RENDER_GRID_CELL_SIZE_M` | 10 |
| `VITE_RENDER_ANTIALIAS_ENABLED` | `true` |
| `VITE_RENDER_TARGET_FPS` | 60 |
| `VITE_RENDER_SCALAR_RANGE_MIN` / `MAX` | 0 / 255 |

### XR

| Variable | Default |
| --- | --- |
| `VITE_XR_ENABLED` | `true` |
| `VITE_XR_HAND_TRACKING_ENABLED` | `false` |
| `VITE_XR_HAPTIC_FEEDBACK_ENABLED` | `true` |

### Logging / performance / topology

| Variable | Default |
| --- | --- |
| `VITE_LOG_ENABLED` | `true` |
| `VITE_LOG_LEVEL` | `info` |
| `VITE_LOG_PERFORMANCE` | `true` |
| `VITE_LOG_INTERACTIONS` | `false` |
| `VITE_LOG_MAX_MESSAGES` | `1000` |
| `VITE_PERF_MONITORING_ENABLED` | `true` |
| `VITE_PERF_FPS_MONITOR_INTERVAL_MS` | `1000` |
| `VITE_PERF_MEMORY_WARNING_THRESHOLD_MB` | `512` |
| `VITE_PERF_AUTO_OPTIMIZE_ENABLED` | `true` |
| `VITE_PERF_AUTO_OPTIMIZE_FPS_THRESHOLD` | `30` |
| `VITE_TOPOLOGY_THRESHOLD_MIN` / `MAX` / `DEFAULT` | `0` / `255` / `0` |

---

## Project layout

```
CIVA_Reduction/
├── backend/
│   ├── main.py              # FastAPI app, /api/health, /api/reduce
│   ├── reduce.py            # TTK + VTK reduction pipeline
│   ├── requirements.txt
│   ├── Dockerfile
│   ├── run.sh               # local venv runner
│   ├── run_ttk.sh           # conda runner
│   └── setup_ttk.sh         # one-shot conda env install
├── data/
│   ├── README.md
│   └── datasets/
│       ├── ctBones.vti
│       ├── ctBones.descriptor.json
│       └── waveletElevation.vti
├── docs/
│   ├── FINAL_PROJECT_REPORT.md
│   ├── PROJECT_STATUS_REPORT.md
│   └── CIVA_Topology_Reduction_Presentation.pptx
├── public/
├── scripts/
│   ├── generate_lod.py            # legacy, pre-backend LOD generator
│   ├── generate_project_docx.py
│   └── generate_topology_pptx.py
├── src/
│   ├── App.tsx                    # Top-level React component (UI + load orchestration)
│   ├── main.tsx
│   ├── config/
│   │   ├── appConfig.ts           # env-var binding & defaults
│   │   ├── types.ts
│   │   └── validator.ts
│   ├── core/
│   │   ├── renderer/
│   │   │   ├── renderer-manager.ts
│   │   │   ├── scene-manager.ts
│   │   │   └── rendering-validation.ts
│   │   └── webxr/
│   │       ├── xr-session-manager.ts
│   │       ├── xr-input-manager.ts
│   │       └── xr-feature-detection.ts
│   ├── data/
│   │   ├── data-loader.ts         # loadVti, loadVolume, loadVtiFromFile, ensureRenderableScalars
│   │   ├── async-loader.ts        # progress / cancellation wrapper, loadDescriptor
│   │   ├── dataset-descriptor.ts  # descriptor schema + auto-derive helper
│   │   ├── lod-manager.ts         # SpatialLODManager
│   │   ├── feature-manager.ts
│   │   └── immutable-assets.ts
│   ├── interaction/
│   │   ├── controller.ts
│   │   └── roi.ts
│   ├── metrics/
│   │   ├── metrics-collector.ts
│   │   ├── performance-monitor.ts
│   │   ├── session-event-log.ts
│   │   └── logging.ts
│   ├── state/
│   │   ├── store.ts               # central reactive store
│   │   ├── reduction-fsm.ts       # phase derivation
│   │   ├── reduction-phase.ts
│   │   └── types.ts
│   └── ui/
├── docker-compose.yml
├── vite.config.ts                 # dev server, proxy, dataset listing endpoint
├── package.json
└── tsconfig*.json
```

---

## Module-by-module reference

### `src/config/`

- **`appConfig.ts`** — Reads `VITE_*` env vars and produces the `DEFAULT_CONFIG` object. Helpers: `getEnvString`, `getEnvNumber`, `getEnvBoolean`, `isReductionApiEnabled()`.
- **`types.ts`** — Shape of `AppConfig` (dataset, LOD, ROI, rendering, XR, logging, performance, topology).
- **`validator.ts`** — `validateConfigOrThrow(cfg)` and `logConfigSummary(cfg)`. Called once at boot from `main.tsx`.

### `src/data/`

- **`data-loader.ts`**
  - `loadVti(url)` — fetches and parses a VTI URL via `vtkXMLImageDataReader.setUrl`.
  - `loadVtiFromFile(file: File)` — wraps a picked `File` in a Blob URL and reuses `loadVti`. Same active-scalar setup as the backend response path.
  - `loadVolume(datasetId, lodLevel, signal?, persistenceThreshold?)` — entry point for backend-or-static loads. Tries `loadVolumeFromBackend` first if the API is enabled; falls back to `loadVolumeStatic`.
  - `loadVolumeFromBackend(datasetId, lodLevel, persistenceThreshold?, signal?)` — `POST /api/reduce`, parses VTI bytes from a Blob URL.
  - `loadVolumeWithProgress(datasetId, lodLevel, onProgress?, signal?, persistenceThreshold?)` — main UI path, emits `resolve / fetch / decode / done` progress phases.
  - `getVtiUrlForLod(basePath, datasetId, lodLevel)` — `${basePath}/${datasetId}_<lod>.vti` (or `.vti` for `full`).
  - `getVtkjsIndexUrlForLod(...)` — same for vtk.js HttpDataSetReader index.json layout.
  - `volumeFromVtkImageData(data)` — converts a `vtkImageData` into a `LoadedVolume` plain object.
  - `getVolumeStatsFromVtkImageData(data)` — pulls dimensions / spacing / voxel count for the dashboard.
  - `ensureRenderableScalars(image)` (private) — auto-activates a 1-component scalar; if only multi-component arrays exist, synthesizes a 1-component array from component 0 of the first array. Runs after every load.
  - `DataLoader` class — facade that exposes the above as instance methods.
- **`async-loader.ts`**
  - `loadVolumeAsync(options)` — wraps `loadVolumeWithProgress` with a structured `LoadResult { data, descriptor, lodLevel }` and `LoadError` semantics.
  - `loadDescriptor(datasetId, signal?)` — fetches `<basePath>/<id>.descriptor.json`; returns `null` if missing or invalid.
- **`dataset-descriptor.ts`**
  - Types `DatasetDescriptor`, `ScalarFieldDescriptor`, `ResolutionLevel`.
  - `voxelCountFromDimensions(dims)`.
  - `resolutionToLodLevel(level)` — maps `coarse / medium / fine / feature` → `low / medium / high / full`.
  - `descriptorFromVtkImageData(id, data)` — auto-derives a descriptor from a loaded volume so users can drop in any VTI without writing JSON.
- **`lod-manager.ts`** — `SpatialLODManager` tracks one active LOD per region (`global` by default), emits `LODStateTransition` events, and logs them.
- **`feature-manager.ts`** — Feature dataset registry (slice / iso / threshold dependencies).
- **`immutable-assets.ts`** — Read-only asset markers.

### `src/state/`

- **`store.ts`** — Central reactive store. Exports `store` with actions:
  - `setTopologyThreshold(value)` — TTK persistence parameter (0–255 slider, normalized to [0,1] in App).
  - `setDisplayIntensityMin(value)` — opacity floor.
  - `setLodLevel(level)`, `setAutoLodByDistance(enabled)`.
  - `setFeatureSliceEnabled / setFeatureDimVolume / setFeatureIsosurfaceEnabled / setFeatureIsosurfaceValue / setFeatureThresholdEnabled / setFeatureThresholdRange`.
  - `setRoiWireframe / setRoiRefinementEnabled / setRoiRadiusWorld / setRoiCenterNorm`.
  - `setReductionBackendEnabled(bool)` — used by health probe + error fallback.
  - `setActiveScalarField / rollbackScalarField`.
  - `setVolumeStats(stats)`, `resetExplorationState()`.
  - `subscribe(listener)` returns an unsubscribe function.
- **`reduction-fsm.ts`** — `snapshotFsmState(ctx)` derives the canonical phase from current context. `FSM_TRANSITION_TABLE` documents allowed events.
- **`reduction-phase.ts`** — `ReductionPhase` enum-like type.
- **`types.ts`** — `AppState`, `ReductionState`, `ScalarState`, `VolumeStats`, `LodLevel`.

### `src/core/renderer/`

- **`renderer-manager.ts`** — Owns the vtk.js `RenderWindow`, `OpenGLRenderWindow`, `Renderer`, and `Interactor`. Methods: `init`, `startRenderLoop`, `stopRenderLoop`, `render`, `setFrameDeltaHook`, `dispose`.
- **`scene-manager.ts`** — Builds and updates the scene:
  - `setVolumeData(image, preset, displayIntensitySlider)` — installs the volume mapper, transfer functions, and unit distance.
  - `setDisplayIntensityFromSlider(value)` — updates the OTF without reload.
  - `setSlicePlaneEnabled(bool)`.
  - `setIsosurfaceEnabled(bool, value)`.
  - `setThresholdRegion(bool, min, max)`.
  - `setContextualVolumeDim(bool)`.
  - `setRoiWireframe(bool, radius, centerWorld?)` — cyan high-resolution sphere; reuses the existing source on subsequent calls so the sphere moves smoothly.
  - `setRoiRefinementVolumeData(image, radius, centerWorld?)` — ImageCropFilter + secondary VolumeMapper.
  - `getLastWorldBounds()`, `getVolumeCenter()`, `resetCamera()`, `dispose()`.
  - `buildCurrentOTFForDataRange(dMin, dMax)` (private) — unified opacity ramp that works at any slider value (solves the "black cube at intensity 0" bug).
- **`rendering-validation.ts`** — `validateVolumeData(image)` checks bounds, scalar presence, and NaN-free buffers before rendering.

### `src/core/webxr/`

- **`xr-feature-detection.ts`** — `supportsImmersiveVR()` with a Promise-based feature probe.
- **`xr-session-manager.ts`** — `XRSessionManager` wraps `navigator.xr.requestSession('immersive-vr')`.
- **`xr-input-manager.ts`** — Tracks XR controller inputs.

### `src/interaction/`

- **`controller.ts`** — Camera / pointer interaction wiring.
- **`roi.ts`** — ROI utility helpers.

### `src/metrics/`

- **`metrics-collector.ts`** — `MetricsCollector` records frame deltas, action latencies (`lod_switch_to_stable`, `feature_*_to_stable`, `roi_refinement_to_stable`, `volume_load`), exposes `getPerformanceSnapshot()` and `exportReport()`.
- **`performance-monitor.ts`** — FPS / frame-time aggregation.
- **`session-event-log.ts`** — `sessionEventAppend(name, payload)` and `sessionEventExportObject()`.
- **`logging.ts`** — Leveled logger with config gating.

### `src/ui/`

- Dashboard styling primitives.

### `src/App.tsx`

- Mounts the renderer / scene / metrics collector inside a single `useEffect`.
- Subscribes to the store and translates state diffs into scene-manager calls.
- Tracks `datasetIdRef` so the same reload pipeline is reused when the user changes the dataset dropdown.
- Builds the dashboard panel with all controls.
- Handles `handlePickFile` (file picker), `handleFileChange` (parse + apply uploaded file), `handleSelectDataset` (dropdown change), `handleEnterVR / handleExitVR`, `handleExportSession`.

### `vite.config.ts`

- `serveDataDatasets()` plugin:
  - `GET /data/datasets/index.json` → returns `{"datasets": [...]}` (auto-discovery).
  - `GET /data/datasets/<file>` → streams the file from disk.
- Proxies `/api/*` to `VITE_REDUCTION_PROXY_TARGET` for both dev and preview.
- Force-includes `@kitware/vtk.js` in `optimizeDeps`.

### `backend/`

- **`main.py`** — FastAPI app. CORS open. Routes:
  - `GET /api/health` — `{ ok, vtk, ttk, dataDir }`.
  - `GET /api/ttk` — diagnostic info.
  - `GET /api/reduce?datasetId=&level=&persistenceThreshold=` — same as POST, query-string variant.
  - `POST /api/reduce` — accepts `{ datasetId, level, persistenceThreshold? }`, returns reduced VTI bytes plus an `X-Reduction-Metadata` header.
- **`reduce.py`** — TTK pipeline:
  - `_read_vti(path)` / `_write_vti_to_bytes(image)`.
  - `reduce_with_ttk(image, threshold)` — `ttkPersistenceDiagram` → vtkThreshold on `Persistence` array → `ttkTopologicalSimplification`. Falls back to `ttkTopologicalSimplificationByPersistence` if the explicit pipeline fails.
  - `_estimate_max_persistence(image)` — used to map a normalized `[0,1]` slider value into TTK's absolute persistence units.
  - `_spatial_lod_grid(image, level)` — applies `vtkImageShrink3D` (1×, 1×, 2×, 4×).
  - `reduce_volume(source_path, level, persistence_threshold?)` — orchestrates the full pipeline and emits a structured event log on stderr.

---

## Topology reduction pipeline

When the user moves the persistence slider or picks an LOD other than `full`, the frontend sends:

```json
POST /api/reduce
{
  "datasetId": "ctBones",
  "level": "medium",
  "persistenceThreshold": 0.42
}
```

Backend logic:

1. Load source `${DATA_DIR}/${datasetId}.vti` via `vtkXMLImageDataReader`.
2. If `persistenceThreshold == 0`, skip TTK and pass through.
3. If `0 < threshold ≤ 1`, multiply by `_estimate_max_persistence(image)` to get an absolute threshold.
4. Run `reduce_with_ttk(image, abs_threshold)` — TTK simplifies topological features below the threshold.
5. Apply `vtkImageShrink3D` for the chosen `level` (full/high = 1×, medium = 2×, low = 4×).
6. Return the resulting VTI as `application/octet-stream` plus `X-Reduction-Metadata` JSON header:

```json
{
  "usedTTK": true,
  "persistenceThreshold": 105.6,
  "persistenceThresholdNormalized": 0.42,
  "reductionMode": "ttk-topological+lod",
  "level": "medium",
  "outputDimensions": [128, 128, 128],
  "outputSpacing": [2, 2, 2],
  "outputOrigin": [0, 0, 0],
  "ttkAvailable": true
}
```

The frontend logs this to the console under `[Reduction metadata]`.

### Verifying TTK is doing real work

```bash
curl -sS http://localhost:8000/api/health
# {"ok":true,"vtk":true,"ttk":true,"dataDir":"/data/datasets"}

curl -sS -X POST http://localhost:8000/api/reduce \
  -H 'Content-Type: application/json' \
  -d '{"datasetId":"ctBones","level":"medium","persistenceThreshold":0.5}' \
  -o /tmp/reduced.vti -D -
# Look for: X-Reduction-Metadata: {"usedTTK":true,...}
```

In the browser, open DevTools → Network → filter `reduce` → response headers should include `X-Reduction-Metadata` with `"usedTTK":true`.

---

## Adding and switching datasets

There are three ways to bring a `.vti` file into the app, with different reduction guarantees.

### 1. Drop into `data/datasets/` (recommended; supports TTK reduction)

```bash
cp /path/to/myScan.vti data/datasets/
```

- **No restart needed**: the file is immediately served at `/data/datasets/myScan.vti` and the backend's mounted `/data/datasets` sees it.
- **Refresh the browser once**: the DATASET dropdown auto-discovers `myScan` from `index.json`.
- **Pick `myScan` from the dropdown** — the app loads it through `POST /api/reduce`. LOD and persistence sliders run real TTK on it.

To make it the boot default, set `VITE_DEFAULT_DATASET=myScan` in `.env` and restart Vite.

### 2. Browser file picker (any file on disk; static-only)

Click **Load .vti file** in the DATASET card. The file is parsed locally via a Blob URL — no server round-trip, no reduction. Useful for quick previews of files outside the project.

### 3. Programmatic descriptor (optional)

If you want richer dashboard metadata for a dataset, drop a `<id>.descriptor.json` next to it (see `data/datasets/ctBones.descriptor.json` for the schema). Otherwise the descriptor is auto-derived at load time from the parsed `vtkImageData`.

### Scalar handling for non-standard files

The loader handles files that vtk.js would normally choke on:

- **No active scalar set**: the first 1-component PointData array is auto-activated.
- **Only multi-component arrays** (e.g. `waveletElevation.vti`'s 2-component `RDataWithElevation`): a new 1-component array is synthesized from component 0 and added as the active scalar.
- **No PointData arrays at all**: validation fails with a clear error (CellData-only files are not yet auto-converted; use ParaView or `vtkCellDataToPointData` to convert offline if you hit this).

---

## API reference

### `POST /api/reduce`

Body:

```ts
{
  datasetId: string;       // bare id; ".vti" is appended on the server
  level: 'full' | 'high' | 'medium' | 'low';
  persistenceThreshold?: number;  // 0–1 normalized, or absolute (>1)
}
```

Response: `application/octet-stream` (a complete VTI file) plus header `X-Reduction-Metadata: <JSON>`.

Error codes:
- **400** — invalid `datasetId`.
- **404** — file not found in `DATA_DIR`.
- **500** — TTK / VTK pipeline failure (full traceback in body).
- **503** — TTK or VTK not importable in the backend's Python env.

### `GET /api/reduce`

Same as POST, with query-string parameters: `?datasetId=&level=&persistenceThreshold=`.

### `GET /api/health`

```json
{ "ok": true, "vtk": true, "ttk": true, "dataDir": "/data/datasets" }
```

`ok` is true only when both VTK and TTK are importable.

### `GET /api/ttk`

Diagnostic info:

```json
{
  "python": "/opt/conda/envs/civa-backend/bin/python",
  "vtkAvailable": true,
  "ttkAvailable": true,
  "dataDir": "/data/datasets"
}
```

### `GET /data/datasets/index.json`

Vite middleware route (dev only):

```json
{ "datasets": ["ctBones", "waveletElevation"] }
```

---

## Engineering standards

This project enforces strict discipline:

- **ESM-only** — no CommonJS in `src/`.
- **TypeScript strict mode** — no implicit `any`, explicit return types, prefer `unknown` before narrowing.
- **No hardcoded paths** — dataset and file paths come from env vars or descriptor JSON. The loader falls back to descriptor-from-data when no file exists.
- **Zero-warning ESLint** — `npm run lint` enforces `--max-warnings 0`.
- **Prettier-formatted** — run `npm run format` before committing.
- **One responsibility per module** — UI in `ui/` and `App.tsx`, state in `state/`, scene/render in `core/renderer/`, data in `data/`. No cross-imports that violate this layering.

---

## Troubleshooting

### "Reduction: Backend unreachable" in yellow

The frontend can't reach `/api/health`. Three causes:

1. The backend isn't running. `docker compose up --build` or run uvicorn locally.
2. `VITE_REDUCTION_PROXY_TARGET` in `.env` doesn't match the port the backend is listening on. Update one or the other.
3. CORS in custom deployments — for non-proxied setups, ensure `VITE_REDUCTION_API_URL` is the full backend URL and the backend allows your origin.

### "Volume validation failed: No point scalars"

Older code threw this on multi-component VTI files. The current loader auto-handles it via `ensureRenderableScalars`. If you still see it: the file likely has only CellData scalars — convert offline with ParaView's "Cell Data to Point Data" filter and re-import.

### "Failed to load resource ... chunk-XXXXX.js (404)"

Vite's dep-optimizer cache went stale.

```bash
rm -rf node_modules/.vite
npm run dev -- --force
```

Then **hard-refresh** the browser (Cmd + Shift + R / Ctrl + Shift + R).

### Black cube at low intensity

Fixed in the current OTF code. If you somehow get this again: the unified ramp in `SceneManager.buildCurrentOTFForDataRange` always keeps a hard rising edge so the volume stays visible regardless of slider value. Verify `scene-manager.ts` is current.

### Two dev servers running on 5173 and 5174

A previous Vite process didn't release its port. Kill them:

```bash
pkill -9 -f vite
```

Then start one fresh instance with `npm run dev`.

### TTK errors on Apple Silicon

`topologytoolkit` has no `osx-arm64` build on conda-forge. Run the backend in Docker (`docker compose up`), which uses an `amd64` Linux image. The frontend on the host talks to it over `localhost:8000`.

---

## Changelog of recent improvements

This project went through a focused round of UX and correctness work:

### Display intensity OTF rewrite (`src/core/renderer/scene-manager.ts`)
- Replaced the branching cutaway/grayscale OTF with a single unified ramp.
- Slider 0 = full visibility; higher values progressively hide low-scalar voxels.
- Sharp rising edge at the threshold preserves opacity under vtk.js's `PROPORTIONAL` mode (the previous smooth ramp collapsed to zero opacity in uniform regions, producing the "black cube at intensity 0" bug).

### Robust scalar handling (`src/data/data-loader.ts`)
- Added `ensureRenderableScalars(image)` that runs after every load.
- Activates the first 1-component PointData array if available.
- Synthesizes a 1-component array from component 0 of any multi-component PointData array if no 1-component array exists. This makes `waveletElevation.vti` (a 2-component vector field) renderable.

### Dataset auto-discovery
- New Vite middleware route `GET /data/datasets/index.json` lists every `.vti` in the data folder.
- Dashboard now shows a dropdown of all available datasets; selecting one triggers a TTK-reduced reload through the existing pipeline.

### File picker for one-off loads
- New `loadVtiFromFile(file)` reads a picked `File` via Blob URL, reusing the same `setUrl` path as the static and backend-reduce loads.
- "Load .vti file" button in the DATASET card; works with any file on disk, no `.env` change.
- Auto-derives the descriptor from the loaded `vtkImageData` (`descriptorFromVtkImageData`), so users don't have to write JSON.

### Movable ROI with XYZ sliders
- Added `roiCenterNorm: [x, y, z] | null` to the store (normalized [0,1]³, survives LOD changes).
- `SceneManager.setRoiWireframe` and `setRoiRefinementVolumeData` accept an optional center.
- New panel in the dashboard with X / Y / Z + Radius sliders (only visible when ROI wireframe is on).
- Sphere is reused on subsequent calls via `setCenter / setRadius` — no flicker while dragging.
- Cyan color, higher resolution (48 × 48), opacity 0.85.

### Dashboard cleanup
- Removed verbose paragraphs under each section.
- DATASET card moved to the top of the panel.
- Renamed "Phase 5/6/7" labels to "Features & ROI", "Measurements", "Session".

### Backend port alignment
- `VITE_REDUCTION_PROXY_TARGET` defaults to `http://localhost:8000` to match `docker-compose.yml`.

---

## License

MIT — see [LICENSE](LICENSE).

---

## Acknowledgements

- [Kitware vtk.js](https://github.com/Kitware/vtk-js) — WebGL scientific visualization.
- [Topology ToolKit](https://topology-tool-kit.github.io/) — persistence-based topological simplification.
- [VTK](https://vtk.org/) — the canonical visualization library.
- [FastAPI](https://fastapi.tiangolo.com/) — Python web framework powering the backend.
