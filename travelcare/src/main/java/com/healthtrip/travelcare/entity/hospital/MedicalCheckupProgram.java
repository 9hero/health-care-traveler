package com.healthtrip.travelcare.entity.hospital;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class MedicalCheckupProgram extends BaseTimeEntity {

    @Builder
    public MedicalCheckupProgram(Long id, Hospital hospital, MedicalCheckupProgram referenceCheckupProgram, ProgramType programType, String programName, BigDecimal priceForMan, BigDecimal priceForWoman, List<ProgramCheckupItem> programCheckupItems, List<ProgramCategory> programCategories, String elements) {
        this.id = id;
        this.hospital = hospital;
        this.referenceCheckupProgram = referenceCheckupProgram;
        this.programType = programType;
        this.programName = programName;
        this.priceForMan = priceForMan;
        this.priceForWoman = priceForWoman;
        this.programCheckupItems = programCheckupItems = new ArrayList<>();
        this.programCategories = programCategories = new ArrayList<>();
        this.elements = elements;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,optional = false)
    private Hospital hospital;

    @Setter
    @OneToOne(optional = true,fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "extra_checkup_program_id")
    private MedicalCheckupProgram referenceCheckupProgram;

    @Enumerated(EnumType.STRING)
    private ProgramType programType;

    private String programName;

    private BigDecimal priceForMan;

    private BigDecimal priceForWoman;
    private String elements;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "medicalCheckupProgram",cascade = CascadeType.PERSIST)
    private List<ProgramCheckupItem> programCheckupItems;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "medicalCheckupProgram",cascade = {CascadeType.PERSIST})
    private List<ProgramCategory> programCategories;

    public void setCategories(List<ProgramCategory> programCategories) {
        this.programCategories = programCategories;
    }
    public void addCategory(ProgramCategory programCategory) {
        this.programCategories.add(programCategory);
    }


    @Getter
    public enum ProgramType {
        In_Depth("정밀검사"),
        Total("종합검진"),
        Inpatient("입원검진"),
        Specialized("특화검진");
        private final String description;

        ProgramType(String description) {
            this.description = description;
        }
    }

}
