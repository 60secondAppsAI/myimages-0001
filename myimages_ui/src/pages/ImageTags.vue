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
            <imageTag-table
            v-if="imageTags && imageTags.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:imageTags="imageTags"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-image-tags="getAllImageTags"
             >

            </imageTag-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ImageTagTable from "@/components/ImageTagTable";
import ImageTagService from "../services/ImageTagService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ImageTagTable,
  },
  data() {
    return {
      imageTags: [],
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
    async getAllImageTags(sortBy='imageTagId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ImageTagService.getAllImageTags(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.imageTags.length) {
					this.imageTags = response.data.imageTags;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching imageTags:", error);
        }
        
      } catch (error) {
        console.error("Error fetching imageTag details:", error);
      }
    },
  },
  mounted() {
    this.getAllImageTags();
  },
  created() {
    this.$root.$on('searchQueryForImageTagsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllImageTags();
    })
  }
};
</script>
<style></style>
