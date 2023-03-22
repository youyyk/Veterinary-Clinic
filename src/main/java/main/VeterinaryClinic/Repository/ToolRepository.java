package main.VeterinaryClinic.Repository;

import main.VeterinaryClinic.Model.Medicine;
import main.VeterinaryClinic.Model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool findByToolID(long toolID);
    List<Tool> findBySoftDeleted(boolean softDeleted);
}