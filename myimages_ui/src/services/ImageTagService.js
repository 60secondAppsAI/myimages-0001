import http from "../http-common"; 

class ImageTagService {
  getAllImageTags(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/imageTag/imageTags`, searchDTO);
  }
 

  get(imageTagId) {
    return this.getRequest(`/imageTag/${imageTagId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/imageTag?field=${matchData}`, null);
  }

  addImageTag(data) {
    return http.post("/imageTag/addImageTag", data);
  }

  update(data) {
  	return http.post("/imageTag/updateImageTag", data);
  }
  
  uploadImage(data,imageTagId) {
  	return http.postForm("/imageTag/uploadImage/"+imageTagId, data);
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

export default new ImageTagService();
