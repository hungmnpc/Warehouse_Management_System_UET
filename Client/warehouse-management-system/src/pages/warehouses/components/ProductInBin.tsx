import { UUID } from 'crypto';
import { useEffect, useState } from 'react';
import { getProductsInBin } from '../../../utils/request';

interface Props {
    binId: UUID | any;
}

export const ProductsInBin = ({ binId }: Props) => {
    const [products, setProducts] = useState<any[]>([]);

    const getProducts = () => {
        getProductsInBin(binId)
            .then((response) => {
                console.log('response', response);
                if (response.success) {
                    setProducts(response.data);
                }
            })
            .catch((error) => console.error(error));
    };
    useEffect(() => {
        if (binId) {
            getProducts();
        }
    }, [binId]);

    return (
        <div>
            {products.length == 0 ? (
                <p
                    style={{
                        fontSize: '16px',
                    }}
                >
                    This Bin is empty
                </p>
            ) : (
                products.map((product, index) => (
                    <p
                        style={{
                            fontSize: '16px',
                        }}
                    >
                        {product.productName} ({product.quantity})
                    </p>
                ))
            )}
        </div>
    );
};
