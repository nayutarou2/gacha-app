export interface ResultData {
  // id: number
  // result: string;
  gachaResult:string[];
  gachaResultDetail:number[];
}

// 👇 新しく追加する：APIから返ってくる「大外」のデータの型
export interface GachaResponse {
  gachaResult: string[]; // ここで「配列」を指定する
}