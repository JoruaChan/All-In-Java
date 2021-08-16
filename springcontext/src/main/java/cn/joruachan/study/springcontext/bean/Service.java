package cn.joruachan.study.springcontext.bean;

/**
 * 文件介绍<br>
 * 功能详细描述
 *
 * @author JoruaChan
 */
public class Service {

    //    @Autowired
    public Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void service() {
        Object o = new Object();
        System.out.println("----save object------");

        repository.save(o);

        System.out.println("----remove object------");
        repository.remove(o);
    }

//    public void setRepository(Repository repository) {
//        this.repository = repository;
//    }
}
