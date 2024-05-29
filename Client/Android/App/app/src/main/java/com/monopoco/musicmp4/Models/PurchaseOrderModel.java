package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class PurchaseOrderModel {
    @SerializedName("createdBy")
    private String createdBy;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("lastModifiedBy")
    private String lastModifiedBy;
    @SerializedName("lastModifiedDate")
    private String lastModifiedDate;
    @SerializedName("isDeleted")
    private boolean isDeleted;
    @SerializedName("id")
    private UUID id;
    @SerializedName("status")
    private String status;
    @SerializedName("poCode")
    private String poCode;
    @SerializedName("referenceNumber")
    private String referenceNumber;
    @SerializedName("inboundDate")
    private String inboundDate;
    @SerializedName("arrivalDate")
    private String arrivalDate;
    @SerializedName("comment")
    private String comment;
    @SerializedName("supplierId")
    private String supplierId;
    @SerializedName("supplierName")
    private String supplierName;
    @SerializedName("warehouseId")
    private String warehouseId;
    @SerializedName("warehouseName")
    private String warehouseName;
    @SerializedName("employeeFullName")
    private String employeeFullName;
    @SerializedName("employeeName")
    private String employeeName;
    @SerializedName("employeeId")
    private String employeeId;
    @SerializedName("deadLineToStock")
    private String deadLineToStock;


    public PurchaseOrderModel(String createdBy, String createdDate, String lastModifiedBy, String lastModifiedDate, boolean isDeleted, UUID id, String status, String poCode, String referenceNumber, String inboundDate, String arrivalDate, String comment, String supplierId, String supplierName, String warehouseId, String warehouseName, String employeeFullName, String employeeName, String employeeId, String deadLineToStock) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.isDeleted = isDeleted;
        this.id = id;
        this.status = status;
        this.poCode = poCode;
        this.referenceNumber = referenceNumber;
        this.inboundDate = inboundDate;
        this.arrivalDate = arrivalDate;
        this.comment = comment;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.employeeFullName = employeeFullName;
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.deadLineToStock = deadLineToStock;
    }

    public PurchaseOrderModel() {
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDeadLineToStock() {
        return deadLineToStock;
    }

    public void setDeadLineToStock(String deadLineToStock) {
        this.deadLineToStock = deadLineToStock;
    }
}
