import { resolve } from 'path'
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
  // root: 'src',
  plugins: [react()],
  resolve: {
    alias: {
      // srcディレクトリのエイリアスを設定
      '@': resolve(__dirname, './src'),
    },
  },
  server: {
    host: '0.0.0.0',
    port: 3000
  },
  build: {
    // distフォルダに出力
    outDir: resolve(__dirname, 'dist'),
    // 存在しないときはフォルダを作成する
    emptyOutDir: true,
    rollupOptions: {
      // entry-pointがあるindex.htmlのパス
      input: {
        '': resolve(__dirname, 'index.html'),
      },
      // bundle.jsを差し替えする
      output: {
        entryFileNames: 'assets/[name]/bundle.js',
      },
    },
  },
});
