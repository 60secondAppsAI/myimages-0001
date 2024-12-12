
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3 justify-content-end">
      <div class="col-8"></div>
       <div class="col-auto">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by BusinessUnit#, Location#, Operator#, City, State, FirstName, LastName…"
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Historys</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalHistorys = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalHistorys">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add History</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="HistoryId" type="text" placeholder="Enter HistoryId" v-model="historyToAdd.historyId"></base-input>
  <base-input label="UserProfileId" type="text" placeholder="Enter UserProfileId" v-model="historyToAdd.userProfileId"></base-input>
  <base-input label="ImageAction" type="text" placeholder="Enter ImageAction" v-model="historyToAdd.imageAction"></base-input>
  <base-input label="ActionDate" type="text" placeholder="Enter ActionDate" v-model="historyToAdd.actionDate"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="historys" :row-key="record => record.HistoryId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <HistoryPictureView :historys="historys" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import HistoryService from "../services/HistoryService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import HistoryPictureView from './HistoryPictureView.vue';


const historysColumns = [
  "historyId",
  "year",
  "date",
  "competitionId",
  "historyId"
]

export default {
  props: {
    historys: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    HistoryPictureView
  },

  data() {
    return {
      modalHistorys: false,
        isTableView: true,

      columns: [
        {
          title: 'History Id',
		dataIndex: 'historyId',
          visible: true,
          scopedSlots: { customRender: 'historyId' },
          sorter: true
          //sorter: (a, b) => a.historyId - b.historyId,
          //sorter: (a, b) => a.historyId.localeCompare(b.historyId),
        },
        {
          title: 'User Profile Id',
		dataIndex: 'userProfileId',
          visible: true,
          scopedSlots: { customRender: 'userProfileId' },
          sorter: true
          //sorter: (a, b) => a.userProfileId - b.userProfileId,
          //sorter: (a, b) => a.userProfileId.localeCompare(b.userProfileId),
        },
        {
          title: 'Image Action',
		dataIndex: 'imageAction',
          visible: true,
          scopedSlots: { customRender: 'imageAction' },
          sorter: true
          //sorter: (a, b) => a.imageAction - b.imageAction,
          //sorter: (a, b) => a.imageAction.localeCompare(b.imageAction),
        },
        {
          title: 'Action Date',
		dataIndex: 'actionDate',
          visible: true,
          scopedSlots: { customRender: 'actionDate' },
          sorter: true
          //sorter: (a, b) => a.actionDate - b.actionDate,
          //sorter: (a, b) => a.actionDate.localeCompare(b.actionDate),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} historys`,
      },

      historys: [],
      historyToAdd: {},

      historysTable: {
        columns: [...historysColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'historyId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderHistorysTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let historysTableData = [];
      for (let i = 0; i < this.historys.length; i++) {
        historysTableData.push({
          id: i,
          historyId: this.historys[i].historyId,
          year: this.historys[i].year,
          date: this.historys[i].date,
          competitionId: this.historys[i].competitionId,
          historyId: this.historys[i].historyId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-historys',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToPromptConfigDetail(id) {
      this.$router.push({ name: 'PromptConfigDetail', params: { promptConfigId: id.toString() }})
    },
    routingToImageRequestDetail(id) {
      this.$router.push({ name: 'ImageRequestDetail', params: { imageRequestId: id.toString() }})
    },
    routingToUserProfileDetail(id) {
      this.$router.push({ name: 'UserProfileDetail', params: { userProfileId: id.toString() }})
    },
    routingToImageDetail(id) {
      this.$router.push({ name: 'ImageDetail', params: { imageId: id.toString() }})
    },
    routingToGalleryDetail(id) {
      this.$router.push({ name: 'GalleryDetail', params: { galleryId: id.toString() }})
    },
    routingToGalleryImageDetail(id) {
      this.$router.push({ name: 'GalleryImageDetail', params: { galleryImageId: id.toString() }})
    },
    routingToTagDetail(id) {
      this.$router.push({ name: 'TagDetail', params: { tagId: id.toString() }})
    },
    routingToImageTagDetail(id) {
      this.$router.push({ name: 'ImageTagDetail', params: { imageTagId: id.toString() }})
    },
    routingToFeedbackDetail(id) {
      this.$router.push({ name: 'FeedbackDetail', params: { feedbackId: id.toString() }})
    },
    routingToImageMetadataDetail(id) {
      this.$router.push({ name: 'ImageMetadataDetail', params: { imageMetadataId: id.toString() }})
    },
    routingToHistoryDetail(id) {
      this.$router.push({ name: 'HistoryDetail', params: { historyId: id.toString() }})
    },
    routingToSettingsDetail(id) {
      this.$router.push({ name: 'SettingsDetail', params: { settingsId: id.toString() }})
    },
    routingToSubscriptionDetail(id) {
      this.$router.push({ name: 'SubscriptionDetail', params: { subscriptionId: id.toString() }})
    },
    routingToPaymentDetail(id) {
      this.$router.push({ name: 'PaymentDetail', params: { paymentId: id.toString() }})
    },
    routingToOrganizationDetail(id) {
      this.$router.push({ name: 'OrganizationDetail', params: { organizationId: id.toString() }})
    },
    routingToUserRoleDetail(id) {
      this.$router.push({ name: 'UserRoleDetail', params: { userRoleId: id.toString() }})
    },
    routingToLicenseDetail(id) {
      this.$router.push({ name: 'LicenseDetail', params: { licenseId: id.toString() }})
    },
    routingToNotificationDetail(id) {
      this.$router.push({ name: 'NotificationDetail', params: { notificationId: id.toString() }})
    },
    routingToEventDetail(id) {
      this.$router.push({ name: 'EventDetail', params: { eventId: id.toString() }})
    },
    routingToInviteDetail(id) {
      this.$router.push({ name: 'InviteDetail', params: { inviteId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForHistorysChanged', this.searchQuery);
		//this.renderHistorysTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalHistorys = false;

      const currentDate = new Date().getTime();
      this.historyToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.historyToAdd);
      console.log(jsonData);
      
      const res = await HistoryService.addHistory(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderHistorysTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}
.header-search {
  width: 300px !important;
  margin-left: auto !important;
}
</style>
