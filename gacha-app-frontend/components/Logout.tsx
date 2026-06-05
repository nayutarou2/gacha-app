import { logoutAction } from "@/app/api/auth";

export default function Logout() {

  return (
    <form action={logoutAction}>
      <button type="submit">
        ログアウト
      </button>
    </form>
  );
}