package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog.core.Constant;
import shop.mtcoding.blog.core.PagingUtil;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardRepository boardRepository;
    @GetMapping({"/","/board"})
    public String index(HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        List<Board> boardList = boardRepository.findAll(page);
        List<Integer> pageNumbers = new ArrayList<>();
        int totalPages = PagingUtil.getTotalPageCount(boardRepository.count());
        request.setAttribute("boardList",boardList);
        int currentPage = page;
//        int nextPage = currentPage+1;
//        int prevPage = currentPage-1;
        int nextPage = (currentPage<totalPages-1)? currentPage+1:totalPages-1;
        int prevPage = (currentPage>0)?currentPage-1 : 0;
        for (int i = 0; i < totalPages; i++) {
            pageNumbers.add(i);
        }
        boolean first = (currentPage == 0);
        boolean last = (currentPage == totalPages-1);
        request.setAttribute("pageNumbers",pageNumbers);
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("nextPage", nextPage);
        request.setAttribute("prevPage", prevPage);
        request.setAttribute("first",first);
        request.setAttribute("last",last);
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {

        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("board",board);
        return "board/updateForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO, HttpServletRequest request){
        System.out.println(requestDTO);
        if (requestDTO.getTitle().length() > 20 || requestDTO.getContent().length() > 20){
            request.setAttribute("status", 400);
            request.setAttribute("msg", "글의 길이가 20자로 제한됩니다.");
            return "error/40x";
        }
        boardRepository.save(requestDTO);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id, BoardRequest.UpdateDTO requestDTO, HttpServletRequest request){

        boardRepository.update(requestDTO, id);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id){

        boardRepository.deleteById(id);
        return "redirect:/";
    }
}
