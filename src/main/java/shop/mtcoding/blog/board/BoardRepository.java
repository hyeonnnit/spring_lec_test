package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.Constant;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public int count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");
        Long count = (Long) query.getSingleResult();
        return count.intValue();
    }
    public List<Board> findAll(int page) {
        int value = page * Constant.PAGING_COUNT;
        Query query= em.createNativeQuery("select * from board_tb order by id desc limit ?,?", Board.class);
        query.setParameter(1,value);
        query.setParameter(2,Constant.PAGING_COUNT);
        List<Board> boardList = query.getResultList();
        return boardList;
    }
    @Transactional
    public void save(BoardRequest.SaveDTO requestDTO) {
        Query query = em.createNativeQuery("insert into board_tb(title,content,author,created_at)values (?,?,?,now())");
        query.setParameter(1,requestDTO.getTitle());
        query.setParameter(2,requestDTO.getContent());
        query.setParameter(3,requestDTO.getAuthor());
        query.executeUpdate();
    }

    public Board findById(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        query.setParameter(1,id);
        Board board = (Board) query.getSingleResult();
        return board;
    }
    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1,id);
        query.executeUpdate();
    }
    @Transactional
    public void update(BoardRequest.UpdateDTO requestDTO, int id) {
        Query query = em.createNativeQuery("update board_tb set title=?, content =? ,author=? where id =?");
        query.setParameter(1,requestDTO.getTitle());
        query.setParameter(2,requestDTO.getContent());
        query.setParameter(3,requestDTO.getAuthor());
        query.setParameter(4,id);
        query.executeUpdate();
    }


}
