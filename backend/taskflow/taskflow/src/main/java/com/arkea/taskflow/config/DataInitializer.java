package com.arkea.taskflow.config;

import com.arkea.taskflow.model.Project;
import com.arkea.taskflow.model.Task;
import com.arkea.taskflow.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProjectRepository projectRepository;

    public DataInitializer(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        projectRepository.deleteAll();

        Project arkia = Project.builder().name("Arkia").description("Migration des données vers une architecture Java 25 / SpringBoot3").build();
        arkia.addTask(Task.builder().title("Analyse des dépendances Maven").status("TERMINEE").build());
        arkia.addTask(Task.builder().title("Configuration de la base H2").status("EN_COURS").build());
        arkia.addTask(Task.builder().title("Mise en place de Spring Security").status("A_FAIRE").build());

        Project taskFlow = Project.builder().name("Taskflow Enterprise").description("Déploiement du gestionnaire de tâches RSE.").build();
        taskFlow.addTask(Task.builder().title("Initialisation du projet Vue 3").status("TERMINEE").build());
        taskFlow.addTask(Task.builder().title("Création des mappers MapStruct").status("TERMINEE").build());
        taskFlow.addTask(Task.builder().title("Configuration du store Pinia").status("A_FAIRE").build());

        projectRepository.save(arkia);
        System.out.println("INSPECTION");
        System.out.println(arkia);
        projectRepository.save(taskFlow);
        System.out.println(taskFlow);
    }
}
