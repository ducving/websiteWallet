package com.example.websitewallet.reponsetory;
import com.example.websitewallet.entity.Classify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassifyRepo extends JpaRepository<Classify,Long> {


}
