package com.lti.demo.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lti.demo.model.Patient;
import com.lti.demo.service.PatientService;

@RestController
@RequestMapping("admission")
public class AdmissionController {
	@Autowired
	private PatientService patientService;
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/physician")
	public List<Object> getEmployees() {

		Object[] employeesList = restTemplate.getForObject("http://Hr/hr/getAll", Object[].class);

		return Arrays.asList(employeesList);
	}

	@RequestMapping("/create")
	public String create(@RequestParam(value = "PId", required = true) String PId, @RequestParam("name") String name,
			@RequestParam("nationality") String nationality) {
		Patient p = patientService.create(PId, name, nationality);
		return p.toString();
	}

	@RequestMapping("/get")
	public Patient getPatient(@RequestParam("PId") String PId) {
		return patientService.getByPId(PId);
	}

	@RequestMapping("/getAll")
	public List<Patient> getAll() {
		return patientService.getAll();
	}

	@RequestMapping("/update")
	public String update(@RequestParam String PId, @RequestParam String name, @RequestParam String nationality) {
		Patient p = patientService.update(PId, name, nationality);
		return p.toString();
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam String PId) {
		patientService.delete(PId);
		return "Deleted" + PId;
	}

	@RequestMapping("/deleteAll")
	public String deleteAll() {
		patientService.deleteAll();
		return "Deleted all records";
	}
}