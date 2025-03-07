<template>
  <div class="dashboard-layout">
    <el-container class="dashboard-wrapper">
      <el-header class="dashboard-header">
        <span class="header-title">Clear Ledger</span>
      </el-header>
      <el-container class="dashboard-content-wrapper">
        <el-aside width="200px" class="dashboard-aside-wrapper">
          <el-menu class="dashboard-aside-menu" @select="onMenuItemSelected">
            <el-menu-item
              v-for="ledger in ledgers"
              :key="`ledger#${ledger.id}`"
              :index="`ledger#${ledger.id}`">
              {{ ledger.name }}
            </el-menu-item>
            <el-menu-item
              key="join-ledger"
              index="join-ledger"
              :disabled="(Object.keys(ledgers)).length >= 3">
              加入账本
            </el-menu-item>
            <el-menu-item
              key="create-ledger"
              index="create-ledger"
              :disabled="(Object.keys(ledgers)).length >= 3">
              创建账本
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
    <join-ledger-dialogue v-model="isJoinLedgerDialogueVisible" />
    <create-ledger-dialogue v-model="isCreateLedgerDialogueVisible" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue"
import { useLedgerStore, useUserStore } from "@/store"

import * as LedgerApi from "@/api/ledger"
import { Ledger } from "@/types"
import JoinLedgerDialogue from "@/components/join-ledger-dialogue.vue"
import CreateLedgerDialogue from "@/components/create-ledger-dialogue.vue"
import { useRouter } from "vue-router"

const router = useRouter()

const { isAuthenticated, user } = useUserStore()
const ledgerStore = useLedgerStore()

const ledgers = ref<Record<string, Ledger>>({})

const isJoinLedgerDialogueVisible = ref<boolean>(false)
const isCreateLedgerDialogueVisible = ref<boolean>(false)

const onMenuItemSelected = (key: string) => {
  if (key == "create-ledger") {
    isCreateLedgerDialogueVisible.value = true
  } else if (key == "join-ledger") {
    isJoinLedgerDialogueVisible.value = true
  } else {
    const pattern = /^ledger#(?<ledgerId>\d+)$/
    const match = key.match(pattern)
    const ledgerId = match?.groups ? match.groups.ledgerId : ""
    router.push({ name: "Ledgers", params: { ledgerId } })
  }
}

onMounted(async () => {
  if (Object.keys(ledgerStore.ledgers).length == 0) {
    if (isAuthenticated) {
      ledgerStore.ledgers = (await LedgerApi.getLedgers()).reduce((acc, item) => {
        acc[item.id] = item
        return acc
      }, {} as Record<string, Ledger>)
    }
  }

  ledgers.value = ledgerStore.ledgers
})
</script>

<style scoped lang="less">
.dashboard-layout {
  .dashboard-wrapper {
    .dashboard-header {
      display: flex;
      align-items: center;
      background-color: #888;

      .header-title {
        font-size: 18px;
      }
    }

    .dashboard-content-wrapper {
      height: calc(100vh - 60px);

      .dashboard-aside-wrapper {
        .dashboard-aside-menu {
          height: 100%;
        }
      }
    }
  }
}
</style>
