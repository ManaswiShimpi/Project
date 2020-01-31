package com.lti.demo.Controller;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping("/physician")
	public List<Object> getEmployees() {

		Object[] employeesList = restTemplate.getForObject("http://Hr/hr/getAll", Object[].class);

		return Arrays.asList(employeesList);
	}

	@PostMapping("/create")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public String create(@RequestParam(value = "PId", required = true) String PId, @RequestParam("name") String name,
			@RequestParam("nationality") String nationality) {
		Patient p = patientService.create(PId, name, nationality);
		return p.toString();
	}
	
	@GetMapping("/get")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Patient getPatient(@RequestParam String PId) {
		return patientService.getByPId(PId);
	}

	@GetMapping("/get/{PId}")
	public Patient getPatientByPId(@PathVariable String PId) {
		return patientService.getByPId(PId);
	}
	
	@GetMapping("/getAll")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public List<Patient> getAll() {
		return patientService.getAll();
	}

	@PutMapping("/update")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String update(@RequestParam String PId, @RequestParam String name, @RequestParam String nationality) {
		Patient p = patientService.update(PId, name, nationality);
		return p.toString();
	}
	
	 @PutMapping("/update1")
	 public String update1(@RequestBody Patient patient) { Patient p =
	  patientService.update1(patient.getPId(), patient.getname(), patient.getnationality()); return p.toString(); }
	 

	@DeleteMapping("delete")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String delete(@RequestParam String PId) {
		patientService.delete(PId);
		return "Deleted" + PId;
	}

	@DeleteMapping("/deleteAll")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String deleteAll() {
		patientService.deleteAll();
		return "Deleted all records";
	}
}