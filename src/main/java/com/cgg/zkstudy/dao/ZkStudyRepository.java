package com.cgg.zkstudy.dao;

import com.cgg.zkstudy.entity.ZkStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author cgg
 */

@Repository
public interface ZkStudyRepository extends JpaRepository<ZkStudy, Long> {


}
