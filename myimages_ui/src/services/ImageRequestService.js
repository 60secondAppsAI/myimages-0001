import http from "../http-common"; 

class ImageRequestService {
  getAllImageRequests(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/imageRequest/imageRequests`, searchDTO);
  }
 

  get(imageRequestId) {
    return this.getRequest(`/imageRequest/${imageRequestId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/imageRequest?field=${matchData}`, null);
  }

  addImageRequest(data) {
    return http.post("/imageRequest/addImageRequest", data);
  }

  update(data) {
  	return http.post("/imageRequest/updateImageRequest", data);
  }
  
  uploadImage(data,imageRequestId) {
  	return http.postForm("/imageRequest/uploadImage/"+imageRequestId, data);
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

export default new ImageRequestService();
