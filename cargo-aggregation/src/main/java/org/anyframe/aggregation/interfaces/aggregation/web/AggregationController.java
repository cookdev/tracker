package org.anyframe.aggregation.interfaces.aggregation.web;

import org.anyframe.aggregation.interfaces.aggregation.facade.AggregationServiceFacade;
import org.anyframe.aggregation.interfaces.aggregation.facade.dto.DashboardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggregationController {

	@Autowired
	private AggregationServiceFacade aggregationServiceFacade;
	
	
	/**
	 * Admin dashboard 정보를 조회한다.
	 * @return
	 */
	@RequestMapping(value="/dashboards", method=RequestMethod.GET)
	public DashboardDto getDashboard() {
		DashboardDto dashboards = aggregationServiceFacade.getDashboard();
		return dashboards;
	}
}
