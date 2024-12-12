
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

              <h5 class="card-title">Settingss</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalSettingss = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalSettingss">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add Settings</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="SettingsId" type="text" placeholder="Enter SettingsId" v-model="settingsToAdd.settingsId"></base-input>
  <base-input label="UserProfileId" type="text" placeholder="Enter UserProfileId" v-model="settingsToAdd.userProfileId"></base-input>
  <base-input label="Theme" type="text" placeholder="Enter Theme" v-model="settingsToAdd.theme"></base-input>
  <base-input label="NotificationEnabled" type="text" placeholder="Enter NotificationEnabled" v-model="settingsToAdd.notificationEnabled"></base-input>
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
            <a-table :columns="columns" :data-source="settingss" :row-key="record => record.SettingsId" :pagination="pagination"
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
            <SettingsPictureView :settingss="settingss" />
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
import SettingsService from "../services/SettingsService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import SettingsPictureView from './SettingsPictureView.vue';


const settingssColumns = [
  "settingsId",
  "year",
  "date",
  "competitionId",
  "settingsId"
]

export default {
  props: {
    settingss: {
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
    SettingsPictureView
  },

  data() {
    return {
      modalSettingss: false,
        isTableView: true,

      columns: [
        {
          title: 'Settings Id',
		dataIndex: 'settingsId',
          visible: true,
          scopedSlots: { customRender: 'settingsId' },
          sorter: true
          //sorter: (a, b) => a.settingsId - b.settingsId,
          //sorter: (a, b) => a.settingsId.localeCompare(b.settingsId),
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
          title: 'Theme',
		dataIndex: 'theme',
          visible: true,
          scopedSlots: { customRender: 'theme' },
          sorter: true
          //sorter: (a, b) => a.theme - b.theme,
          //sorter: (a, b) => a.theme.localeCompare(b.theme),
        },
        {
          title: 'Notification Enabled',
		dataIndex: 'notificationEnabled',
          visible: true,
          scopedSlots: { customRender: 'notificationEnabled' },
          sorter: true
          //sorter: (a, b) => a.notificationEnabled - b.notificationEnabled,
          //sorter: (a, b) => a.notificationEnabled.localeCompare(b.notificationEnabled),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} settingss`,
      },

      settingss: [],
      settingsToAdd: {},

      settingssTable: {
        columns: [...settingssColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'settingsId',           // Column to sort by
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

    async renderSettingssTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let settingssTableData = [];
      for (let i = 0; i < this.settingss.length; i++) {
        settingssTableData.push({
          id: i,
          settingsId: this.settingss[i].settingsId,
          year: this.settingss[i].year,
          date: this.settingss[i].date,
          competitionId: this.settingss[i].competitionId,
          settingsId: this.settingss[i].settingsId,
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

	  this.$emit('get-all-settingss',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
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
    	this.$root.$emit('searchQueryForSettingssChanged', this.searchQuery);
		//this.renderSettingssTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalSettingss = false;

      const currentDate = new Date().getTime();
      this.settingsToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.settingsToAdd);
      console.log(jsonData);
      
      const res = await SettingsService.addSettings(jsonData);

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
    this.renderSettingssTable();
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
