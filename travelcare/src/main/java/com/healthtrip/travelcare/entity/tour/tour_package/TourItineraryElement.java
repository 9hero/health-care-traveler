package com.healthtrip.travelcare.entity.tour.tour_package;

import com.healthtrip.travelcare.entity.BaseTimeEntity;
import com.healthtrip.travelcare.repository.dto.request.TourItineraryElementRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TourItineraryElement extends BaseTimeEntity {

    @Builder
    public TourItineraryElement(Long id, TourItinerary tourItinerary, List<TourPlaceList> tourPlaceLists, String title, ElementType elementType, Short sequence) {
        this.id = id;
        this.tourItinerary = tourItinerary;
        this.tourPlaceLists = tourPlaceLists;
        this.title = title;
        this.elementType = elementType;
        this.sequence = sequence;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
//    @JoinColumn
    private TourItinerary tourItinerary;

    // 구성요소의 장소목록 (장소와 일정구성요소 중간 테이블)
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "tourItineraryElement",fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private List<TourPlaceList> tourPlaceLists;


    public void addTourPlaceList(TourPlaceList tourPlaceLists) {
        if (this.tourPlaceLists == null){
            this.tourPlaceLists = new ArrayList<>();
        }
        this.tourPlaceLists.add(tourPlaceLists);
    }

    private String title;

    // front에서 사용할 타입 게시물타입, 이동타입, 정보타입
    @Enumerated(EnumType.STRING)
    @Column(name = "show_type")
    private ElementType elementType;

    // 일정 순서 1:도착 -> 2:이동 -> 3....n:종료
    private Short sequence;
    public enum ElementType {
        MOVE
    }

    public void updateElement(TourItineraryElementRequest request) {
        this.title = request.getTitle();
        this.elementType = request.getElementType();
        this.sequence = request.getSequence();
    }

}
