<template>
  <div class="container">
    <!-- 用户交互区 -->
    <el-form ref="formRef" :model="query" label-width="auto">
      <el-form-item label="" prop="name">
        <el-input
          v-model="query.oNumber"
          placeholder="请输入订单号"
          :prefix-icon="Search"
          style="width: 490px"
          size="large"
          clearable
        />
        <el-button
          style="margin-left: 20px; margin-right: 5px"
          @click="getList"
          type="primary"
          plain
          >查找</el-button
        >
        <el-button @click="button('insert')" type="success">添加</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据展示区 -->
    <el-table
      border
      table-layout="auto"
      :data="list"
      stripe
      width="600"
      height="750"
    >
      <el-table-column type="index" width="80" />

      <el-table-column prop="uId" label="用户" />

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

      <el-table-column fixed="right" label="数据操作">
        <template #default="scope">
          <el-button type="warning" @click="button('update', scope.row)">
            编辑数据
          </el-button>
          <el-button type="danger" @click="button('delete', scope.row)">
            删除数据
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页区 -->
    <el-pagination
      v-model:current-page="query.current"
      v-model:page-size="query.size"
      :total="query.total"
      :page-sizes="[10, 20]"
      layout="total, prev, pager, next, sizes"
      @size-change="getList()"
      @current-change="getList()"
    />

    <!-- 模态框 -->
    <el-dialog
      v-model="model"
      :close="close()"
      destroy-on-close
      :title="modelTitle"
      width="500"
      align-center
    >
      <el-form
        ref="formRef"
        v-if="!isInfo"
        :model="modelData"
        label-width="auto"
      >
        <el-form-item label="用户ID">
          <el-input v-model="modelData.uId" disabled placeholder="请输入" />
        </el-form-item>
        <el-form-item label="订单号">
          <el-input v-model="modelData.oNumber" placeholder="请输入" />
        </el-form-item>

        <el-form-item label="座位号">
          <el-input v-model="modelData.oDetails" placeholder="请输入" />
        </el-form-item>

        <el-form-item label="创建时间">
          <el-date-picker
            v-model="modelData.oDate"
            type="datetime"
            format="YYYY年MM月DD日 HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>

        <el-form-item label="订单信息">
          <el-input v-model="modelData.oMessage" placeholder="请输入" />
        </el-form-item>

        <el-form-item label="订单状态">
          <el-select
            v-model="modelData.oStatus"
            class="m-2"
            placeholder="Select"
            size="large"
            style="width: 240px"
          >
            <el-option
              v-for="(item, index) in status"
              :key="index"
              :label="item"
              :value="index"
            />
          </el-select>
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancel">取消</el-button>
          <el-button type="primary" @click="done">确认</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import { order } from "@/api/request";
import { ElMessage } from "element-plus";

const list = reactive([
  {
    oId: 0,
    uId: 0,
    oNumber: "",
    oDetails: "",
    oMessage: "",
    oDate: "",
    oStatus: 0,
  },
]);

const query = ref({
  current: 1,
  size: 10,
  pages: 0,
  total: 0,
  oNumber: "",
  oStatus: "",
  uId: "",
});

const timeColor = ["primary", "success"];

const model = ref(false);
const modelTitle = ref("模态框");
const method = ref("");
const isInfo = ref(false);

interface Order {
  uId: number | string;
  oId: number | string;
  oNumber: string;
  oDetails: string;
  oMessage: string;
  oDate: string;
  oStatus: number | string;
}

let modelNull = {
  oId: "",
  uId: "",
  oNumber: "",
  oDetails: "",
  oMessage: "",
  oDate: "",
  oStatus: "",
};

let modelData: Order = modelNull;

const status = ["已取消", "待处理", "已完成"];
const statusColor = ["info", "warning", "success"];

const getList = async () => {
  const { result, message }: any = await order.list(query.value);
  list.splice(0);
  list.push(...result.records);
  query.value = Object.assign({}, query.value, result.pagination);
  ElMessage({
    message: message,
    type: "success",
  });
};
const insert = async (item: object) => {
  const { result, message }: any = await order.insert(item);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
  getList();
};
const update = async (item: object) => {
  const { result, message }: any = await order.update(item);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
  getList();
};
const del = async (item: object) => {
  const { result, message }: any = await order.delete(item);
  if (result.is) {
    ElMessage({
      message: message,
      type: "success",
    });
  } else {
    ElMessage.error(message);
  }
  getList();
};

const button = (operation: string, row: Order = modelNull) => {
  const operations: { [key: string]: Function } = {
    insert: () => {
      modelData = reactive({ ...modelNull });
      modelTitle.value = "添加数据";
    },
    update: () => {
      modelData = reactive({ ...row });
      modelTitle.value = "编辑数据";
    },
    delete: () => {
      modelData = reactive({ ...row });
      modelTitle.value = "删除数据";
    },
  };

  operations[operation]();
  method.value = operation;
  model.value = true;
};

const done = () => {
  const operations: { [key: string]: Function } = {
    insert: () => {
      insert(modelData);
    },
    update: () => {
      update(modelData);
      console.log(method.value);
    },
    delete: () => {
      del(modelData);
      console.log(method.value);
    },
  };

  operations[method.value]();
  isInfo.value = false;
  model.value = false;
};

const cancel = () => {
  isInfo.value = false;
  model.value = false;
};

let i = 0;
const close = () => {
  i++;
  if (!model.value && i > 2) {
    isInfo.value = false;
    model.value = false;
    i = 2;
  }
};

getList();
</script>

<style scoped></style>
