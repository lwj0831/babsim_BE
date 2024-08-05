package likelion.babsim.domain.point.repository;

import likelion.babsim.domain.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point,Long> {
    List<Point> findAllByMemberId(String memberId);
}
