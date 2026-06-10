import { loginAction } from '@/src/lib/actions/authActions';
import styles from './login.module.css';
import AuthForm from '@/src/components/auth/AuthForm';

type PageProps = {
  searchParams: Promise<{ [key: string]: string | string[] | undefined }>;
};

export default async function Login({ searchParams }: PageProps) {
  const resolvedSearchParams = await searchParams;

  const isSuccess = resolvedSearchParams.success === 'true';

  const initialState = {
    success: false,
    email: '',
  };

  return (
    <div className={styles.body}>
      <div className={styles.login}>
        {isSuccess && <div>ユーザー登録が完了しました</div>}
        <h1 className={styles.h1}>ガチャアプリ</h1>
      </div>
      <AuthForm auth="login" action={loginAction} initialState={initialState} />
    </div>
  );
}
