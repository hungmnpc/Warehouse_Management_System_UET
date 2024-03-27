import { BaseRecord } from "@refinedev/core";
import { UUID } from "crypto";

export interface IUser extends DTO{
    createdBy: string,
    createdDate: string,
    firstName: string,
    lastName: string,
    id: UUID,
    roleName: string,
    userName: string
}

export interface TablePaginationActionsProps {
    count: number;
    page: number;
    rowsPerPage: number;
    onPageChange: (
      event: React.MouseEvent<HTMLButtonElement>,
      newPage: number,
    ) => void;
}

export interface DTO extends BaseRecord{};

export interface IResponse {};

export interface IResponsePagination<DTO> extends IResponse {
    result: IResult,
    data: IPagination<DTO>,
}

export interface IPagination<DTO> {
    data: DTO[],
    dataCount: number,
    pageNumber: number,
    pageSize: number
}

export interface IResult {
    message: string,
    ok: boolean,
    responseCode: string,
}