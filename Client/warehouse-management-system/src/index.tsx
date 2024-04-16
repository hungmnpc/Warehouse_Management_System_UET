import React from 'react';
import { createRoot } from 'react-dom/client';

import App from './App';
import GlobalStyles from './components/globalstyle';

const container = document.getElementById('root') as HTMLElement;
const root = createRoot(container);

root.render(
    <React.StrictMode>
        <GlobalStyles>
            <App />
        </GlobalStyles>
    </React.StrictMode>,
);
