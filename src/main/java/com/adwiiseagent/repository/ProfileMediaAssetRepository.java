package com.adwiiseagent.repository;

import com.adwiiseagent.domain.ProfileMediaAsset;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileMediaAssetRepository extends JpaRepository<ProfileMediaAsset, Long>, JpaSpecificationExecutor<ProfileMediaAsset> {}
