//package com.example.MyBookShopApp.config;
//
//import com.example.MyBookShopApp.data.BookRepository;
//import com.example.MyBookShopApp.data.TestEntity;
//
//import com.example.MyBookShopApp.data.TestEntityCrudRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.logging.Logger;
//
//@Configuration
//public class CommandLineRunnerImpl implements CommandLineRunner {
//
//    TestEntityCrudRepository testEntityCrudRepository;
//    BookRepository bookRepository;
//    @Autowired
//    public CommandLineRunnerImpl(TestEntityCrudRepository testEntityCrudRepository, BookRepository bookRepository) {
//        this.testEntityCrudRepository = testEntityCrudRepository;
//        this.bookRepository = bookRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        for (int i = 0 ; i < 5;i++){
//            createTestEntity(new TestEntity());
//        }
//        TestEntity readTestEntity = readTestEntityById(3L);
//        if (readTestEntity != null){
//            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("read " +  readTestEntity.toString());
//        }else {
//            throw new NullPointerException();
//        }
//        TestEntity updateTestEntity = updateTestEntityByID(5L);
//        if (updateTestEntity != null){
//            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update " +  updateTestEntity.toString());
//        }else {
//            throw new NullPointerException();
//        }
////        deleteTestEntityByID(4L);
//
//        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.findBookByAuthor_FirstName("Tisha").toString());
//        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info(bookRepository.customFindAllBooks().toString());
//
//    }
//
////    private void deleteTestEntityByID(long id) {
////        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
////        testEntityCrudRepository.delete(testEntity);
////    }
//
//    private TestEntity updateTestEntityByID(long id) {
//       TestEntity testEntity = testEntityCrudRepository.findById(id).get();
//       testEntity.setData("NEW DATA");
//       testEntityCrudRepository.save(testEntity);
//       return testEntity;
//
//
//    }
//
//    private TestEntity readTestEntityById(long id) {
//        return testEntityCrudRepository.findById(id).get();
//    }
//
//
//    private void createTestEntity(TestEntity entity) {
//        entity.setData(entity.getClass().getSimpleName() + entity.hashCode());
//        testEntityCrudRepository.save(entity);
//
//    }
//}
