package com.monopoco.importservice.entity;

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
@Table(name = "import")
public class Import {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "import_code")
    @Comment("Mã nhập kho")
    private String importCode;

    @Column(name = "import_name")
    @Comment("Tên nhập kho")
    private String importName;

    // More...//
}