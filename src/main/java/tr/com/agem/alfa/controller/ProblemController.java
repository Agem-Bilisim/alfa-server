package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.service.ProblemService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class ProblemController {

	private static final Logger log = LoggerFactory.getLogger(ProblemController.class);

	private final ProblemService problemService;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public ProblemController(ProblemService problemService) {
		this.problemService = problemService;
	}

	@GetMapping("/problem/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "referenceType", required = false) Integer referenceType, Pageable pageable) {
		log.info("Getting problem page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Problem> packages = problemService.getProblems(pageable, search, referenceType);
			result.add("problems", checkNotNull(packages, "Problems not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find problems, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

}
