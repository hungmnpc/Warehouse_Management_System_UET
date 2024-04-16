import './GlobalStyle.css';

export interface GlobalStylesProps {
    children?: React.ReactNode;
}

function GlobalStyles({ children }: GlobalStylesProps) {
    return children;
}

export default GlobalStyles;
