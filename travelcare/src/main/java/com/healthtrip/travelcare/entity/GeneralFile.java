package com.healthtrip.travelcare.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class GeneralFile extends BaseTimeEntity{

    @Builder
    public GeneralFile(Long id, String url, String fileName, String originalName, long fileSize) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.originalName = originalName;
        this.fileSize = fileSize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String fileName;

    private String originalName;

    private long fileSize;

}
