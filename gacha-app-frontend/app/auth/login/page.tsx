import LoginForm from "@/components/LoginForm";
import styles from "./login.module.css";


type PageProps = {
  searchParams: Promise<{ [key: string]: string | string[] | undefined }>;
};

export default async function Login({ searchParams }: PageProps) {

  const resolvedSearchParams = await searchParams;

  const isSuccess = resolvedSearchParams.success === 'true';

  return (
    <div className={styles.body}>
      <div className={styles.login}>
        {
          isSuccess &&
          <div>
            ユーザー登録が完了しました
          </div>
        }
        <h1 className={styles.h1} >ガチャアプリ</h1>
      </div>
      <LoginForm />
    </div>
  );
}