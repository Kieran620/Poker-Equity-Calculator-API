package KieranKhan.PokerEquityAPI.Solver;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/equitycalculator")
public class SolverController {

    private final SolverService service = new SolverService();
    private Solver solver;

    @GetMapping("/hello")
    public List<String> hello() {
        return service.hello();
    }

    @GetMapping("/data")
    public List<String> getEquity() {
        if(solver == null)
            return null;
        return solver.exec2();
    }

    @PostMapping("/data")
    public void addData(@RequestBody List<String> cards) {

        solver = new Solver(cards.subList(0, 4), cards.subList(4, cards.size()));
    }

//    @PostMapping("/test")
//    public void addData(@RequestBody List<String> a) {
//        System.out.println(a);
//    }

}
