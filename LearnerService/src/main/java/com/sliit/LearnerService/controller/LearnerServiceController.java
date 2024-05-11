package com.sliit.LearnerService.controller;

import com.sliit.LearnerService.dao.dto.LearnerProgressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sliit.LearnerService.dao.dto.RequestDto;
import com.sliit.LearnerService.dao.dto.ResponseDto;
import com.sliit.LearnerService.service.i.LearnerService;

import java.util.List;

@RestController
@RequestMapping("/api/LearnerService")
public class LearnerServiceController {
	
	@Autowired
	LearnerService learnerService;
	
	@RequestMapping(value = "/enrollCourse", method = RequestMethod.POST)
	public @ResponseBody ResponseDto enrollCourse(@RequestBody RequestDto requestDto) {
		return learnerService.enrollCourse(requestDto);
	}
	
	@RequestMapping(value = "/cancelCourseEnroll", method = RequestMethod.POST)
	public @ResponseBody ResponseDto cancelCourseEnroll(@RequestBody RequestDto requestDto) {
		return learnerService.cancelCourseEnroll(requestDto);
	}
	
	@RequestMapping(value = "/getUserEnrolledCourses", method = RequestMethod.POST)
	public @ResponseBody ResponseDto getUserEnrolledCourses(@RequestBody RequestDto requestDto) {
		return learnerService.findUserEnrolledCoursesByUserId(requestDto);
	}
	
	@RequestMapping(value = "/updateEnrolledCourseContent", method = RequestMethod.POST)
	public @ResponseBody ResponseDto updateEnrolledCourseContent(@RequestBody RequestDto requestDto) {
		return learnerService.updateEnrolledCourseContent(requestDto);
	}

	@RequestMapping(value = "/CourseLearnerProgresses/{courseId}", method = RequestMethod.GET)
	public @ResponseBody List<LearnerProgressDto> getLearnerProgresses(@PathVariable String courseId) {
		System.out.println(courseId);
		return learnerService.retrieveLearnerProgresses(courseId);
	}

	@RequestMapping(value = "/UserLearnerProgresses", method = RequestMethod.GET)
	public @ResponseBody LearnerProgressDto getLearnerProgress(@RequestBody String courseId, @RequestBody String userId) {
		return learnerService.retrieveLearnerProgressOfUser(courseId, userId);
	}
}
