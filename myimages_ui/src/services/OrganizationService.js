import http from "../http-common"; 

class OrganizationService {
  getAllOrganizations(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/organization/organizations`, searchDTO);
  }
 

  get(organizationId) {
    return this.getRequest(`/organization/${organizationId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/organization?field=${matchData}`, null);
  }

  addOrganization(data) {
    return http.post("/organization/addOrganization", data);
  }

  update(data) {
  	return http.post("/organization/updateOrganization", data);
  }
  
  uploadImage(data,organizationId) {
  	return http.postForm("/organization/uploadImage/"+organizationId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new OrganizationService();
