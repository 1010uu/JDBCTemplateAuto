<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="row">
        <div class="col-12">
            <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
                <div class="container-fluid">
                    <!-- 회사로고(logo) -->
                    <a class="navbar-brand" href="#">
                        <img src="https://www.ikosmo.co.kr/images/common/logo_center_v2.jpg" style="width: 120px;">
                    </a>
                    <!-- GNB(Global Navigation Bar : 해당 웹사이트의 전체(global) 메뉴) -->
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">자유게시판</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">자료실</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#">드롭다운</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Link 1</a></li>
                                <li><a class="dropdown-item" href="#">Link 2</a></li>
                                <li><a class="dropdown-item" href="#">Link 3</a></li>
                            </ul>
                        </li>
                    </ul>
                    <!-- 검색폼 -->
                    <form class="mt-3" method="get" action="">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Search" style="width: 200px;">
                        <button class="btn btn-warning" type="submit">
                            <i class="bi-search" style="font-size: 1rem; color: white;"></i>
                        </button>
                    </div>
                    </form>
                    <!-- 회원가입/로그인 링크 -->
                    <ul class="navbar-nav">
                        <!-- 로그인전 -->
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi-person-plus-fill" style="font-size: 1rem;"></i>
                                회원가입
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi-box-arrow-in-right" style="font-size: 1rem;"></i>
                                로그인
                            </a>
                        </li>
                        <!-- 로그인후 -->
                        <!-- <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi-person-lines-fill" style="font-size: 1rem;"></i>
                                회원정보수정
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi-box-arrow-right" style="font-size: 1rem;"></i>
                                로그아웃
                            </a>
                        </li> -->
                    </ul>
                </div>
            </nav>
        </div>
    </div>