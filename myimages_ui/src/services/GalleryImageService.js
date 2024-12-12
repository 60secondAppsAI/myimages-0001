import http from "../http-common"; 

class GalleryImageService {
  getAllGalleryImages(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/galleryImage/galleryImages`, searchDTO);
  }
 

  get(galleryImageId) {
    return this.getRequest(`/galleryImage/${galleryImageId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/galleryImage?field=${matchData}`, null);
  }

  addGalleryImage(data) {
    return http.post("/galleryImage/addGalleryImage", data);
  }

  update(data) {
  	return http.post("/galleryImage/updateGalleryImage", data);
  }
  
  uploadImage(data,galleryImageId) {
  	return http.postForm("/galleryImage/uploadImage/"+galleryImageId, data);
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

export default new GalleryImageService();
