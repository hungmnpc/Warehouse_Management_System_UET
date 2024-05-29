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

export interface ISupplier extends DTO, IAudit {
    id: UUID;
    supplierName: string;
    supplierNumber: string;
    supplierAddress1: string;
    supplierAddress2: string;
}

export interface IPurchaseOrder extends DTO, IAudit {
    id: UUID;
    status: string;
    poCode: string;
    referenceNumber: string;
    inboundDate: string;
    arrivalDate: string;
    comment: string;
    supplierId: string;
    supplierName: string;
    warehouseId: UUID;
    warehouseName: string;
    employeeFullName: string;
    employeeName: string;
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

export interface HistoryData {
    id: any;
    agent_id: UUID;
    agent_type: string;
    content: string;
    ts: number;
    userId: UUID;
    username: string | undefined;
}

export interface HistoryEvent {
    ts: number; // Unix timestamp in milliseconds
    type: string; // Enum or string indicating the type of event
    username: string | undefined; // Username as a string
    userId: UUID; // UUID in string format
    content: string; // Event content
    agentType: string; // Agent type
    agentId: UUID; // UUID in string format
}

export interface IGoodsReceived extends DTO, IAudit {
    id: UUID;
    warehouseName: string;
    warehouseId: UUID;
    poCode: string;
    importRequestCode: string;
    deliveryDate: string;
    estimatedArrivalDate: string;
    status: string;
    employeeId1: string;
    employeeId2: string;
    employeeId3: string;
    employeeId4: string;
}

export interface IPurchaseOrder extends DTO, IAudit {
    id: UUID;
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
