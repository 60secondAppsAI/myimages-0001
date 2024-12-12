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
            <imageMetadata-table
            v-if="imageMetadatas && imageMetadatas.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:imageMetadatas="imageMetadatas"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-image-metadatas="getAllImageMetadatas"
             >

            </imageMetadata-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ImageMetadataTable from "@/components/ImageMetadataTable";
import ImageMetadataService from "../services/ImageMetadataService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ImageMetadataTable,
  },
  data() {
    return {
      imageMetadatas: [],
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
    async getAllImageMetadatas(sortBy='imageMetadataId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ImageMetadataService.getAllImageMetadatas(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.imageMetadatas.length) {
					this.imageMetadatas = response.data.imageMetadatas;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching imageMetadatas:", error);
        }
        
      } catch (error) {
        console.error("Error fetching imageMetadata details:", error);
      }
    },
  },
  mounted() {
    this.getAllImageMetadatas();
  },
  created() {
    this.$root.$on('searchQueryForImageMetadatasChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllImageMetadatas();
    })
  }
};
</script>
<style></style>
