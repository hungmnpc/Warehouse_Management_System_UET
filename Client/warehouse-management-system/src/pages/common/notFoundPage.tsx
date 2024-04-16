import { useCallback, useContext, useEffect, useRef } from 'react';
import './notfound.scss';
import { ColorModeContext } from '../../contexts/color-mode';

export const NotFoundPage = () => {
    const touchRef = useRef<any>(null);

    const themeContext = useContext(ColorModeContext);

    console.log(themeContext);

    const handleMouseMove = useCallback(
        (event: any) => {
            if (touchRef && touchRef.current) {
                touchRef.current.style.top = `${event.pageY}px`;
                touchRef.current.style.left = `${event.pageX}px`;
            }
        },
        [touchRef],
    );

    useEffect(() => {
        window.addEventListener('mousemove', handleMouseMove);
        return () => {
            window.removeEventListener('mousemove', handleMouseMove);
        };
    }, [handleMouseMove]);
    if (themeContext.mode == 'dark') {
        return (
            <div className="container">
                <div className="text">
                    <h1>404</h1>
                    <h2>Uh, Ohh</h2>
                    <h3>Sorry we cant find what you are looking for 'cuz its so dark in here</h3>
                </div>
                <div ref={touchRef} className="torch"></div>
            </div>
        );
    } else {
        return (
            <section className="page_404">
                <div className="container2">
                    <div className="row">
                        <div className="col-sm-12 ">
                            <div className="col-sm-10 col-sm-offset-1  text-center">
                                <div className="four_zero_four_bg">
                                    <h1 className="text-center">404</h1>
                                </div>

                                <div className="contant_box_404">
                                    <h3 className="h2">Look like you're lost</h3>

                                    <p>the page you are looking for not avaible!</p>

                                    <a href="" className="link_404">
                                        Go to Home
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        );
    }
};
