package lab4;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BuddyRepository extends CrudRepository<BuddyInfo, Long> {

    List<BuddyInfo> findByAddress(String address);

    BuddyInfo findById(long id);

}