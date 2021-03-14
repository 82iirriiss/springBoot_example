package com.example.ex03.common.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, ENTITY> {

    private List<DTO> dtoList;
    //현재 페이지, 한 페이지당 데이터 수
    private int page, size;
    //총 페이지 수
    private int totalPage;
    //시작 페이지, 끝 페이지
    private int start, end;
    //이전페이지 존재 유무, 다음페이지 존재 유무
    private boolean prev, next;

    private List<Integer> pageList;

    public PageResultDTO(Page<ENTITY> result, Function<ENTITY, DTO> fn){
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(this.page/10.0)) * 10;
        this.start = tempEnd - 9;
        this.prev = start > 1;
        this.end = this.totalPage < tempEnd ? totalPage : tempEnd;
        this.next = totalPage > end;

        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }
}
