<template>
  <div class="box">
    <el-table
      border
      table-layout="auto"
      :data="data.order"
      stripe
      width="1200"
      height="750">
      <el-table-column type="index" width="80" />

      <el-table-column prop="oNumber" label="订单号" />

      <el-table-column prop="oDetails" label="座位号">
        <template #default="scope">
          <el-tag
            v-for="(i, index) in scope.row.oDetails.split(',')"
            :key="index"
            :style="{ marginRight: '6px' }"
            >{{ i }}</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column prop="oDate" label="创建时间">
        <template #default="scope">
          <el-tag
            :type="timeColor[index]"
            v-for="(i, index) in scope.row.oDate.split('T')"
            :key="index"
            :style="{ marginRight: '6px' }"
            >{{ i }}</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column prop="oMessage" label="订单信息" />

      <el-table-column prop="oStatus" label="订单状态">
        <template #default="scope">
          <el-tag :type="statusColor[scope.row.oStatus]">{{
            status[scope.row.oStatus]
          }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="订单状态">
        <template #default="scope">
          <el-button type="primary" @click="button(scope.row)"
            >查看详情</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination" v-if="data.query.total > 10">
      <el-pagination
        v-model:current-page="data.query.current"
        v-model:page-size="data.query.size"
        :total="data.query.total"
        :page-sizes="[10, 20]"
        layout="total, prev, pager, next, sizes"
        @size-change="getOrder()"
        @current-change="getOrder()" />
    </div>
  </div>

  <el-dialog
    v-model="dialogVisible"
    style="padding: 10px"
    title="订单详情"
    width="800">
    <div v-if="data.from.oStatus !== 2">
      <el-steps :active="active" finish-status="success" align-center>
        <el-step title="确认订单" />
        <el-step title="支付订单" />
        <el-step title="完成订单" />
      </el-steps>
      <Transition>
        <div v-show="active === 0">
          <el-descriptions column="2">
            <el-descriptions-item label="订单编号">{{
              data.from.oNumber
            }}</el-descriptions-item>

            <el-descriptions-item label="订单信息">{{
              data.from.oMessage
            }}</el-descriptions-item>

            <el-descriptions-item label="订单座位"
              ><el-tag
                v-for="(i, index) in data.from.oDetails.split(',')"
                :key="index"
                :style="{ marginRight: '6px' }"
                >{{ i }}</el-tag
              ></el-descriptions-item
            >

            <el-descriptions-item label="创建时间">
              <el-tag
                :type="timeColor[index]"
                v-for="(i, index) in data.from.oDate.split('T')"
                :key="index"
                :style="{ marginRight: '6px' }"
                >{{ i }}</el-tag
              >
            </el-descriptions-item>

            <el-descriptions-item label="订单状态"
              ><el-tag :type="statusColor[data.from.oStatus]">{{
                status[data.from.oStatus]
              }}</el-tag></el-descriptions-item
            >
          </el-descriptions>
          <el-button type="warning" @click="cancel()">取消订单</el-button>
          <el-button type="success" @click="next()">确认订单</el-button>
        </div>
      </Transition>

      <Transition>
        <div v-show="active === 1">
          <el-descriptions column="2">
            <el-descriptions-item label="订单编号">{{
              data.from.oNumber
            }}</el-descriptions-item>

            <el-descriptions-item label="订单信息">{{
              data.from.oMessage
            }}</el-descriptions-item>

            <el-descriptions-item label="订单座位"
              ><el-tag
                v-for="(i, index) in data.from.oDetails.split(',')"
                :key="index"
                :style="{ marginRight: '6px' }"
                >{{ i }}</el-tag
              ></el-descriptions-item
            >

            <el-descriptions-item label="创建时间">
              <el-tag
                :type="timeColor[index]"
                v-for="(i, index) in data.from.oDate.split('T')"
                :key="index"
                :style="{ marginRight: '6px' }"
                >{{ i }}</el-tag
              >
            </el-descriptions-item>

            <el-descriptions-item label="订单状态"
              ><el-tag :type="statusColor[data.from.oStatus]">{{
                status[data.from.oStatus]
              }}</el-tag></el-descriptions-item
            >
          </el-descriptions>
          <el-button type="warning" @click="prev()">取消支付</el-button>
          <el-button type="success" @click="done()">确认支付</el-button>
        </div>
      </Transition>

      <Transition>
        <div v-show="active === 2">
          563
          <el-button @click="next()">123</el-button>
        </div>
      </Transition>
    </div>
    <div v-else>
      <el-descriptions column="2">
        <el-descriptions-item label="订单编号">{{
          data.from.oNumber
        }}</el-descriptions-item>

        <el-descriptions-item label="订单信息">{{
          data.from.oMessage
        }}</el-descriptions-item>

        <el-descriptions-item label="订单座位"
          ><el-tag
            v-for="(i, index) in data.from.oDetails.split(',')"
            :key="index"
            :style="{ marginRight: '6px' }"
            >{{ i }}</el-tag
          ></el-descriptions-item
        >

        <el-descriptions-item label="创建时间">
          <el-tag
            :type="timeColor[index]"
            v-for="(i, index) in data.from.oDate.split('T')"
            :key="index"
            :style="{ marginRight: '6px' }"
            >{{ i }}</el-tag
          >
        </el-descriptions-item>

        <el-descriptions-item label="订单状态"
          ><el-tag :type="statusColor[data.from.oStatus]">{{
            status[data.from.oStatus]
          }}</el-tag></el-descriptions-item
        >
      </el-descriptions>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { order, user } from "@/api/request";
import { reactive, ref } from "vue";

const data = reactive({
  order: [
    {
      oId: 0,
      uId: 0,
      oNumber: "",
      oDetails: "",
      oMessage: "",
      oDate: "",
      oStatus: 0,
    },
  ],
  query: {
    uId: "",
    oNumber: "",
    current: 1,
    size: 10,
    pages: 0,
    total: 0,
  },
  from: {
    oId: 0,
    uId: 0,
    oNumber: "",
    oDetails: "",
    oMessage: "",
    oDate: "",
    oStatus: 0,
  },
});

const timeColor = ["primary", "success"];
const status = ["已取消", "待处理", "已完成"];
const statusColor = ["info", "warning", "success"];

const getOrder = async () => {
  const {
    result: { user: u },
  }: any = await user.isAdmin({
    token: localStorage.getItem("token"),
  });
  data.query.uId = u.uid;
  const { result, message }: any = await order.list(data.query);
  console.log(message);

  data.query = Object.assign({}, data.query, result.pagination);
  data.order.splice(0);
  data.order.push(...result.records);
};

const dialogVisible = ref(false);
const active = ref(0);

const next = () => {
  active.value > 2 ? (active.value = 0) : active.value++;
};
const prev = () => {
  active.value < 1 ? dialogVisible.value === false : active.value--;
};
const cancel = () => {
  order.cancel(data.from);
  dialogVisible.value = false;
  getOrder();
};
const done = () => {
  active.value > 2 ? (active.value = 0) : active.value++;
  order.done(data.from);
  getOrder();
  dialogVisible.value = false;
};
const button = (row: any) => {
  active.value = 0;
  data.from = Object.assign({}, row);
  dialogVisible.value = true;
};
getOrder();
</script>

<style scoped lang="scss">
.box {
  padding: 50px;
  background-color: #fff;
  margin-top: 20px;
  width: 1200px;
}
</style>
