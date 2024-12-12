<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <galleryImage-table
            v-if="galleryImages && galleryImages.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:galleryImages="galleryImages"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-gallery-images="getAllGalleryImages"
             >

            </galleryImage-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GalleryImageTable from "@/components/GalleryImageTable";
import GalleryImageService from "../services/GalleryImageService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GalleryImageTable,
  },
  data() {
    return {
      galleryImages: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllGalleryImages(sortBy='galleryImageId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GalleryImageService.getAllGalleryImages(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.galleryImages.length) {
					this.galleryImages = response.data.galleryImages;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching galleryImages:", error);
        }
        
      } catch (error) {
        console.error("Error fetching galleryImage details:", error);
      }
    },
  },
  mounted() {
    this.getAllGalleryImages();
  },
  created() {
    this.$root.$on('searchQueryForGalleryImagesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGalleryImages();
    })
  }
};
</script>
<style></style>
