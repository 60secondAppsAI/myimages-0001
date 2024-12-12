import http from "../http-common"; 

class LicenseService {
  getAllLicenses(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/license/licenses`, searchDTO);
  }
 

  get(licenseId) {
    return this.getRequest(`/license/${licenseId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/license?field=${matchData}`, null);
  }

  addLicense(data) {
    return http.post("/license/addLicense", data);
  }

  update(data) {
  	return http.post("/license/updateLicense", data);
  }
  
  uploadImage(data,licenseId) {
  	return http.postForm("/license/uploadImage/"+licenseId, data);
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

export default new LicenseService();
