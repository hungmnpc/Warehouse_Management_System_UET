package com.monopoco.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

/**
 * Created by: hungdinh
 * Date: 04/03/2024
 * Project: Server
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    @Comment("Mã hàng hóa")
    private String productCode;

    @Column(name = "product_name")
    @Comment("Tên hàng hóa")
    private String productName;

    // More...//
}