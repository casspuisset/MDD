export interface Comment {
  id: number;
  userId: number;
  userName: string;
  articleId: number;
  content: string;
  createdAt: Date;
  updatedAt: Date;
}
