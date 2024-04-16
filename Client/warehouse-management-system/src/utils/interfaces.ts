import { BaseRecord } from '@refinedev/core';
import { UUID } from 'crypto';

export interface IUser extends DTO, IAudit {
    firstName: string;
    lastName: string;
    id: UUID;
    roleName: string;
    userName: string;
}

export interface IProductCategory extends DTO, IAudit {
    productCategoryId: UUID;
    categoryName: string;
    categoryDescription: string;
}

export interface IProduct extends DTO, IAudit {
    productId: UUID;
    productCode: string;
    productName: string;
    sku: string;
    barcode: string;
    productDescription: string;
    productCategoryDTO: IProductCategory;
    reorderQuantity: number;
    refrigerated: boolean;
    unit: string;
    isPacked: boolean;
    packedWeight: number;
    packedHeight: number;
    packedWidth: number;
    packedDepth: number;
}

export interface IWarehouse extends DTO, IAudit {
    id: UUID;
    warehouseName: string;
    warehouseNameAcronym: string;
    location: string;
    provinceName: string;
    provinceId: string;
    districtId: string;
    districtName: string;
    wardName: string;
    wardId: string;
    type: string;
}

export interface IAudit {
    createdBy: string;
    createdDate: string;
    lastModifiedBy: string;
    lastModifiedDate: string;
}

export interface ICreateUser extends DTO {
    firstName: string;
    lastName: string;
    userName: string;
    password: string;
    roleId: UUID;
}

export interface TablePaginationActionsProps {
    count: number;
    page: number;
    rowsPerPage: number;
    onPageChange: (event: React.MouseEvent<HTMLButtonElement>, newPage: number) => void;
}

export interface DTO extends BaseRecord {}

export interface IResponse {}

export interface IResponseData<TDTO extends DTO = DTO> extends IResponse {
    data?: TDTO;
    result: IResult;
    success: boolean;
}

export interface IResponsePagination<DTO> extends IResponse {
    result: IResult;
    data: IPagination<DTO>;
}

export interface IPagination<DTO> {
    data: DTO[];
    dataCount: number;
    pageNumber: number;
    pageSize: number;
}

export interface IResult {
    message: string;
    ok: boolean;
    responseCode: string;
}

export interface IDropDown {
    key: any;
    value: any;
}

export interface HeaderKey {
    title: string;
    description: string;
    minWidth: number;
}
