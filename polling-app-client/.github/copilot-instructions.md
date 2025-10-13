This repository is a single-page React app (Create React App) for a polling/voting client.

Quick orientation
- Tech: React 16, react-router, Ant Design (antd), react-app-rewired + react-app-rewire-less.
- Entry point: `src/index.js` → `src/app/App.js` (routes and top-level state).
- API layer: `src/util/APIUtils.js` uses fetch and `src/constants/index.js` for `API_BASE_URL` and `ACCESS_TOKEN`.
- Styling: Less variables are applied via `config-overrides.js` (AntD theming).

What to know when changing code
- Authentication: access token stored in localStorage under key `ACCESS_TOKEN` (see `src/constants/index.js`). Most requests include it automatically in `APIUtils.request`.
- Routes & protection: `PrivateRoute` redirects unauthenticated users to `/login` (see `src/common/PrivateRoute.js`). Update `App.js` routing when adding pages.
- API URL: controlled by `REACT_APP_API_BASE_URL` env var; default is `http://localhost:5200/api`.

Build / run / debug
- Dev: `npm start` (uses `react-app-rewired start` from `package.json`). Environment variables read from `.env*` files. For HTTPS set `HTTPS=true`.
- Build: `npm run build` (`react-app-rewired build`). CI: set `CI=true` to make the build fail on lint warnings.
- Tests: `npm test` runs Jest in watch mode via `react-app-rewired test --env=jsdom`.

Project-specific conventions
- AntD imports are wired with `babel-plugin-import` in `config-overrides.js`: prefer `import { Button } from 'antd'` (less styles auto-imported when style: true).
- Use `APIUtils` functions (e.g., `getAllPolls`, `createPoll`) for backend requests rather than calling `fetch` directly.
- Constants: validation limits (max lengths, min lengths) live in `src/constants/index.js`; use them to keep UI and API expectations consistent.
- State bootstrapping: top-level current user is loaded in `App.componentDidMount()` via `getCurrentUser()`; use `handleLogin` and `handleLogout` patterns there to update auth state.

Integration and external dependencies
- Backend API expected to follow REST endpoints under `/api` (e.g., `/auth/signin`, `/polls`, `/users/:username`). See `APIUtils.js` for exact paths.
- AntD theming: tweaks in `config-overrides.js` via `react-app-rewire-less`; changing theme variables requires rebuilding.

When modifying routes or adding pages
- Add new route entries in `src/app/App.js`. For protected pages, wrap with `PrivateRoute authenticated={this.state.isAuthenticated}`.
- Add component-level CSS in the same folder (pattern: each feature folder contains its .js and .css files, e.g. `src/poll/NewPoll.js` and `NewPoll.css`).

Quick examples (search/replace patterns)
- Fetch current user: import and use `getCurrentUser()` from `src/util/APIUtils.js`.
- Add AntD component: `import { Layout, notification } from 'antd'` — the Less loader will include styles.

Safety and testing notes
- Local dev uses `API_BASE_URL` pointing to `localhost:5200` by default; if backend is on another port, set `REACT_APP_API_BASE_URL`.
- The app relies on `localStorage` for tokens — tests that touch auth should mock `localStorage` or use `src/setupTests.js` to initialize a mock.

Files worth inspecting for context
- `config-overrides.js` — build-time AntD setup
- `src/util/APIUtils.js` — all backend integrations
- `src/app/App.js` — routing, auth flow, notifications
- `src/constants/index.js` — env defaults and input constraints
- `src/common/PrivateRoute.js` — route protection pattern

If you are an AI-assistant editing the code
- Prefer small, focused changes. Run `npm start` and `npm test` after edits to validate (see README for CRA debugging tips).
- Preserve the `react-app-rewired` setup unless the user asks to migrate to plain `react-scripts`.

If anything here is unclear or you need more detailed guidelines (lint rules, CI, or backend contract details), ask the repo owner for the `.env` values and CI config.  