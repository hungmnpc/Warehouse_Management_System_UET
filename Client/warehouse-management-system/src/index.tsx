import React from 'react';
import { createRoot } from 'react-dom/client';

import App from './App';
import GlobalStyles from './components/globalstyle';
import { registerLicense } from '@syncfusion/ej2-base';
registerLicense('ORg4AjUWIQA/Gnt2UFhhQlJBfVldXGBWfFN0QXNYdVpxflFAcDwsT3RfQFljTH9Xd0ZnWn9ZeHdVQQ==');

const container = document.getElementById('root') as HTMLElement;
const root = createRoot(container);

root.render(
    // <React.StrictMode>
    <GlobalStyles>
        <App />
    </GlobalStyles>,
    // </React.StrictMode>,
);
