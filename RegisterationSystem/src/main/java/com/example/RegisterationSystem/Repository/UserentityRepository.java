package com.example.RegisterationSystem.Repository;

import com.example.RegisterationSystem.Model.Userentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//long is the primarylkey type
public interface UserentityRepository extends JpaRepository<Userentity, Long> {
 Optional<Userentity> findByUsername(String username);

}
